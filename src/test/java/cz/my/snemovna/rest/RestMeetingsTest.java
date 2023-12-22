package cz.my.snemovna.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import cz.my.snemovna.AbstractSnemovnaTest;
import cz.my.snemovna.RestResponsePage;
import cz.my.snemovna.TestUtils;
import cz.my.snemovna.dto.meetings.MeetingDetailDto;
import cz.my.snemovna.dto.meetings.MeetingDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The test for meeting agenda endpoints.
 */
class RestMeetingsTest extends AbstractSnemovnaTest {

    private static final Long MEETING_ID = 1L;
    private static final Long MEETING_ID_WITH_NO_POINTS = 10L;

    @DatabaseSetup("/testdata/Meetings.xml")
    @Test
    void testGetMeetings() throws Exception {
        final String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/meetings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("page", "0")
                        .queryParam("size", "10")
                        .queryParam("property", "id")
                        .queryParam("direction", "ASC"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        final RestResponsePage<MeetingDto> resultList = objectMapper.readValue(responseJson, new TypeReference<>() {});
        final RestResponsePage<MeetingDto> expected = objectMapper.readValue(
                TestUtils.getResourceContent("testdata/expectedMeetings.json"), new TypeReference<>() {});
        assertArrayEquals(expected.stream().toArray(), resultList.stream().toArray());
    }

    @Test
    void testGetMeetingsBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/meetings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("page", "-1")
                        .queryParam("size", "10")
                        .queryParam("property", "id")
                        .queryParam("direction", "ASC"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DatabaseSetup("/testdata/Meetings.xml")
    @Test
    void testMeetingDetailBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/meetings/" + MEETING_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DatabaseSetup("/testdata/Meetings.xml")
    @Test
    void testMeetingDetailWithoutPoints() throws Exception {
        final String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/meetings/" + MEETING_ID_WITH_NO_POINTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("type", "APPROVED"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(objectMapper.writeValueAsString(new MeetingDetailDto(new ArrayList<>())), response);
    }

    @DatabaseSetup("/testdata/Meetings.xml")
    @Test
    void testMeetingDetailWithPoints() throws Exception {
        final String response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/meetings/" + MEETING_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("type", "PROPOSED"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        final MeetingDetailDto expected = objectMapper.readValue(
                TestUtils.getResourceContent("testdata/expectedMeetingDetailWithPoints.json"),
                new TypeReference<>() {}
        );
        final MeetingDetailDto actual = objectMapper.readValue(response, new TypeReference<>() {});
        assertEquals(expected, actual);
    }
}
