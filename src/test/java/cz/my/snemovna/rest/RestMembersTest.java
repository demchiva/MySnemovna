package cz.my.snemovna.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import cz.my.snemovna.AbstractSnemovnaTest;
import cz.my.snemovna.RestResponsePage;
import cz.my.snemovna.TestUtils;
import cz.my.snemovna.dto.members.MemberDetailDto;
import cz.my.snemovna.dto.votes.VoteDto;
import cz.my.snemovna.dto.votes.VoteMembersDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestMembersTest extends AbstractSnemovnaTest {

    private static final Long MEMBER_ID = 1L;
    private static final Long MEMBER_ID_NOT_FOUND = 10L;

    @DatabaseSetup("/testdata/VotesMembers.xml")
    @Test
    void testGetMembers() throws Exception {
        final String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/members")
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

        final RestResponsePage<VoteDto> resultList = objectMapper.readValue(responseJson, new TypeReference<>() {});
        final RestResponsePage<VoteDto> expected = objectMapper.readValue(
                TestUtils.getResourceContent("testdata/expectedMembers.json"), new TypeReference<>() {});
        assertArrayEquals(expected.stream().toArray(), resultList.stream().toArray());
    }

    @Test
    void testGetMembersBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("page", "0")
                        .queryParam("size", "-1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DatabaseSetup("/testdata/VotesMembers.xml")
    @Test
    void testVoteDetailNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/members/" + MEMBER_ID_NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @DatabaseSetup("/testdata/VotesMembers.xml")
    @Test
    void testMemberDetail() throws Exception {
        final String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/members/" + MEMBER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        final MemberDetailDto actual = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        final MemberDetailDto expected = objectMapper.readValue(
                TestUtils.getResourceContent("testdata/expectedMemberDetail.json"), new TypeReference<>() {}
        );
        assertEquals(expected, actual);
    }

    @DatabaseSetup("/testdata/VotesMembers.xml")
    @Test
    void testMemberVotesResultsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/members/" + MEMBER_ID_NOT_FOUND + "/votes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @DatabaseSetup("/testdata/VotesMembers.xml")
    @Test
    void testMemberVotesResults() throws Exception {
        final String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/members/" + MEMBER_ID + "/votes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        final List<VoteMembersDto> actual = objectMapper.readValue(responseJson, new TypeReference<>() {});
        final List<VoteMembersDto> expected = objectMapper.readValue(
                TestUtils.getResourceContent("testdata/expectedMemberVotesResults.json"), new TypeReference<>() {});
        assertEquals(expected, actual);
    }
}
