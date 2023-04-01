package cz.my.snemovna.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import cz.my.snemovna.AbstractSnemovnaTest;
import cz.my.snemovna.RestResponsePage;
import cz.my.snemovna.TestUtils;
import cz.my.snemovna.dto.meetings.MeetingDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The test for meeting agenda endpoints.
 */
class RestMeetingsTest extends AbstractSnemovnaTest {

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
                .getContentAsString(Charset.defaultCharset());

        final RestResponsePage<MeetingDto> resultList = objectMapper.readValue(responseJson, new TypeReference<>() {});
        final RestResponsePage<MeetingDto> expected = objectMapper.readValue(
                TestUtils.getResourceContent("testdata/expectedMeetings.json"), new TypeReference<>() {});
        assertArrayEquals(expected.stream().toArray(), resultList.stream().toArray());
    }
}
