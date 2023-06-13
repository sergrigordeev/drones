package com.musala.sg.drones.container.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.musala.sg.drones.domain.core.api.dto.CargoDto;
import com.musala.sg.drones.domain.usecases.api.DroneResponse;
import com.musala.sg.drones.domain.usecases.api.DroneSearchQuery;
import com.musala.sg.drones.domain.usecases.api.drones.availability.GetAvailableDronesResponse;
import com.musala.sg.drones.domain.usecases.api.drones.availability.GetAvailableDronesUsecase;
import com.musala.sg.drones.domain.usecases.api.drones.battery.GetDroneBatteryLevelResponse;
import com.musala.sg.drones.domain.usecases.api.drones.battery.GetDroneBatteryLevelUsecase;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.check.CheckCargoForDroneUsecase;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.check.CheckCargoQuery;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.check.CheckCargoResponse;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.check.MedicationResponse;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.load.LoadMedicationCommand;
import com.musala.sg.drones.domain.usecases.api.drones.cargo.load.LoadMedicationUsecase;
import com.musala.sg.drones.domain.usecases.api.drones.registration.RegisterDroneCommand;
import com.musala.sg.drones.domain.usecases.api.drones.registration.RegisterDroneUsecase;
import com.musala.sg.drones.domain.usecases.exception.DroneNotFoundException;
import com.musala.sg.drones.domain.usecases.exception.DroneRegistrationException;
import com.musala.sg.drones.domain.usecases.exception.LoadMedicationException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DroneControllerTest {
    public static final String API_DRONES = "/api/v1/drones";
    public static final String API_DRONE_MEDICATION = "/api/v1/drones/{serialNumber}/medication";
    public static final String API_DRONE_BATTERY = "/api/v1/drones/{serialNumber}/battery";
    public static final String API_DRONES_AVAILABLE = "/api/v1/drones/available";

    MockMvc mockMvc;
    @InjectMocks
    DroneController controller;
    @Mock
    RegisterDroneUsecase registerDroneUsecase;
    @Mock
    LoadMedicationUsecase mockLoadMedicationUsecase;
    @Mock
    CheckCargoForDroneUsecase mockCheckCargoForDroneUsecase;
    @Mock
    GetAvailableDronesUsecase mockGetAvailableDronesUsecase;
    @Mock
    GetDroneBatteryLevelUsecase mockGetDroneBatteryLevelUsecase;

    @BeforeEach
    public void setup() {
       ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();

        var msgConvertor = new MappingJackson2HttpMessageConverter();
        msgConvertor.setObjectMapper(mapper);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ApiExceptionHandler())
                .setMessageConverters(msgConvertor)
                .build();
    }

    @Nested
    class RegisterDroneTest {
        @SneakyThrows
        @Test
        void that_throws_404_when_request_body_incorrect() {

            ResultActions response = mockMvc.perform(
                    post(API_DRONES)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                        "serialNumber":"",
                                        "model":"",
                                        "maxWeight":0
                                    }
                                    """));

            response
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.errorCode", is(400)))
                    .andExpect(jsonPath("$.message", is("validation failed")))
                    .andExpect(jsonPath("$.fieldsErrors.serialNumber[0]", is("length must be between 1 and 200")))
                    .andExpect(jsonPath("$.fieldsErrors.model[0]", is("must not be blank")))
                    .andExpect(jsonPath("$.fieldsErrors.maxWeight[0]", is("must be between 1 and 500")));
        }

        @SneakyThrows
        @Test
        void that_throws_404_when_drone_can_not_be_registered() {

            RegisterDroneCommand command = new RegisterDroneCommand("SN", "model", 1);
            doThrow(new DroneRegistrationException("the big problem")).when(registerDroneUsecase).execute(command);

            ResultActions response = mockMvc.perform(
                    post(API_DRONES)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                        "serialNumber":"SN",
                                        "model":"model",
                                        "maxWeight":1
                                    }
                                    """));

            response
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.errorCode", is(400)))
                    .andExpect(jsonPath("$.message", is("the big problem")));
        }

        @SneakyThrows
        @Test
        void that_a_new_drone_has_been_registered() {

            RegisterDroneCommand command = new RegisterDroneCommand("SN", "model", 1);
            DroneResponse mockResponse = new DroneResponse("SN", "IDLE", 1, 0);
            doReturn(mockResponse).when(registerDroneUsecase).execute(command);

            ResultActions response = mockMvc.perform(
                    post(API_DRONES)
                            .contentType(APPLICATION_JSON)
                            .content("""
                                    {
                                        "serialNumber":"SN",
                                        "model":"model",
                                        "maxWeight":1
                                    }
                                    """));

            response
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.serialNumber", is("SN")))
                    .andExpect(jsonPath("$.state", is("IDLE")))
                    .andExpect(jsonPath("$.availableWeight", is(1)))
                    .andExpect(jsonPath("$.batteryLevel", is(0)));
        }
    }

    @Nested
    class LoadTest {
        @SneakyThrows
        @Test
        void that_throws_404_when_drone_does_not_found() {
            CargoDto cargoDto = new CargoDto("name", "CODE", 1, "url");
            LoadMedicationCommand command = new LoadMedicationCommand("SN", cargoDto);
            doThrow(new DroneNotFoundException(new DroneSearchQuery("SN"))).when(mockLoadMedicationUsecase).execute(command);

            ResultActions result = mockMvc.perform(
                    put(API_DRONE_MEDICATION, "SN")
                            .content("""
                                    {
                                        "name": "name",
                                        "code":"CODE",
                                        "weight":1,
                                        "imageUrl":"url"
                                    }
                                    """)
                            .contentType(APPLICATION_JSON)
            );

            result
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.errorCode", is(404)))
                    .andExpect(jsonPath("$.message", is("No drone with SN SN has been found")));
        }

        @SneakyThrows
        @Test
        void that_throws_400_when_request_body_incorrect() {
            CargoDto cargoDto = new CargoDto("name", "CODE", 1, "url");
            LoadMedicationCommand command = new LoadMedicationCommand("SN", cargoDto);

            ResultActions result = mockMvc.perform(
                    put(API_DRONE_MEDICATION, "SN")
                            .content("""
                                    {
                                        "name": "",
                                        "code":"",
                                        "weight":-1,
                                        "imageUrl":"url"
                                    }
                                    """)
                            .contentType(APPLICATION_JSON)
            );

            result
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.errorCode", is(400)))
                    .andExpect(jsonPath("$.message", is("validation failed")))
                    .andExpect(jsonPath("$.fieldsErrors.name[0]", is("must not be blank")))
                    .andExpect(jsonPath("$.fieldsErrors.code[0]", is("must not be blank")))
                    .andExpect(jsonPath("$.fieldsErrors.weight[0]", is("must be greater than or equal to 1")));
        }

        @SneakyThrows
        @Test
        void that_throws_400_when_load_throws_CargoLoadException_incorrect() {
            CargoDto cargoDto = new CargoDto("name", "CODE", 1, "url");
            LoadMedicationCommand command = new LoadMedicationCommand("SN", cargoDto);
            doThrow(new LoadMedicationException("the big problem")).when(mockLoadMedicationUsecase).execute(command);

            ResultActions result = mockMvc.perform(
                    put(API_DRONE_MEDICATION, "SN")
                            .content("""
                                    {
                                        "name": "name",
                                        "code":"CODE",
                                        "weight":1,
                                        "imageUrl":"url"
                                    }
                                    """)
                            .contentType(APPLICATION_JSON)
            );

            result
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.errorCode", is(400)))
                    .andExpect(jsonPath("$.message", is("the big problem")));
        }

        @SneakyThrows
        @Test
        void that_cargo_has_been_loaded() {

            ResultActions result = mockMvc.perform(
                    put(API_DRONE_MEDICATION, "SN")
                            .content("""
                                    {
                                        "name": "name",
                                        "code":"CODE",
                                        "weight":1,
                                        "imageUrl":"url"
                                    }
                                    """)
                            .contentType(APPLICATION_JSON)
            );

            result
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class CheckLoadedMedicationTest {
        @SneakyThrows
        @Test
        void that_throws_404_when_drone_does_not_found() {

            CheckCargoQuery query = new CheckCargoQuery("SN");
            doThrow(new DroneNotFoundException(new DroneSearchQuery("SN"))).when(mockCheckCargoForDroneUsecase).execute(query);

            ResultActions result = mockMvc.perform(
                    get(API_DRONE_MEDICATION, "SN")
            );

            result
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.errorCode", is(404)))
                    .andExpect(jsonPath("$.message", is("No drone with SN SN has been found")));
        }

        @SneakyThrows
        @Test
        void that_returns_medication_for_drone() {

            CheckCargoQuery query = new CheckCargoQuery("SN");
            CheckCargoResponse response = new CheckCargoResponse(500, 400, List.of(
                    mockMedication("name1", "CODE1", 40),
                    mockMedication("name2", "CODE2", 60)
            ));
            doReturn(response).when(mockCheckCargoForDroneUsecase).execute(query);

            ResultActions result = mockMvc.perform(
                    get(API_DRONE_MEDICATION, "SN")
            );

            result
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.maxWeight", is(500)))
                    .andExpect(jsonPath("$.availableWeight", is(400)))
                    .andExpect(jsonPath("$.medications[0].name", is("name1")))
                    .andExpect(jsonPath("$.medications[0].code", is("CODE1")))
                    .andExpect(jsonPath("$.medications[0].weight", is(40)))
                    .andExpect(jsonPath("$.medications[1].name", is("name2")))
                    .andExpect(jsonPath("$.medications[1].code", is("CODE2")))
                    .andExpect(jsonPath("$.medications[1].weight", is(60)));
        }

        private MedicationResponse mockMedication(String name, String code, int weight) {
            String imgUrl = "url";
            return new MedicationResponse(name, code, weight, imgUrl);
        }
    }


    @Nested
    class CheckDroneBatteryTest {

        @SneakyThrows
        @Test
        void that_throws_404_when_drone_does_not_found() {

            DroneSearchQuery query = new DroneSearchQuery("SN");
            doThrow(new DroneNotFoundException(new DroneSearchQuery("SN"))).when(mockGetDroneBatteryLevelUsecase).execute(query);

            ResultActions result = mockMvc.perform(
                    get(API_DRONE_BATTERY, "SN")
            );

            result
                    .andExpect(status().isNotFound())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.errorCode", is(404)))
                    .andExpect(jsonPath("$.message", is("No drone with SN SN has been found")));
        }

        @SneakyThrows
        @Test
        void that_returns_batteryLevel_for_drone() {

            DroneResponse mockResponse1 = new DroneResponse("SN1", "IDLE", 1, 11);
            DroneResponse mockResponse2 = new DroneResponse("SN2", "IDLE", 2, 22);
            GetAvailableDronesResponse response = new GetAvailableDronesResponse(List.of(mockResponse1, mockResponse2));
            doReturn(response).when(mockGetAvailableDronesUsecase).execute(any());

            ResultActions result = mockMvc.perform(
                    get(API_DRONES_AVAILABLE));

            result
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.drones[0].serialNumber", is("SN1")))
                    .andExpect(jsonPath("$.drones[0].state", is("IDLE")))
                    .andExpect(jsonPath("$.drones[0].availableWeight", is(1)))
                    .andExpect(jsonPath("$.drones[0].batteryLevel", is(11)))
                    .andExpect(jsonPath("$.drones[1].serialNumber", is("SN2")))
                    .andExpect(jsonPath("$.drones[1].state", is("IDLE")))
                    .andExpect(jsonPath("$.drones[1].availableWeight", is(2)))
                    .andExpect(jsonPath("$.drones[1].batteryLevel", is(22)));
        }
    }

    @Nested
    class CheckAvailableDronesTest {
        @SneakyThrows
        @Test
        void that_returns_medication_for_drone() {

            DroneSearchQuery query = new DroneSearchQuery("SN");
            GetDroneBatteryLevelResponse response = new GetDroneBatteryLevelResponse("SN", 50, OffsetDateTime.of(100, 10, 10, 9, 59, 10, 100, ZoneOffset.UTC));
            doReturn(response).when(mockGetDroneBatteryLevelUsecase).execute(query);

            ResultActions result = mockMvc.perform(
                    get(API_DRONE_BATTERY, "SN"));

            result
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(APPLICATION_JSON))
                    .andExpect(jsonPath("$.serialNumber", is("SN")))
                    .andExpect(jsonPath("$.batteryLevel", is(50)))
                    .andExpect(jsonPath("$.dateTime", is("0100-10-10T09:59:10.0000001Z")));
        }
    }
}