package cz.my.snemovna.service.parsers.votes;

import cz.my.snemovna.jpa.model.votes.MemberVotes;
import cz.my.snemovna.jpa.repository.votes.MemberVotesRepository;
import cz.my.snemovna.service.parsers.AgendaSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberVotesParser1 extends AbstractVoteWithYearSourceParser<MemberVotes, MemberVotes.MemberVotesId> {

    public MemberVotesParser1(MemberVotesRepository repository) {
        super(repository);
    }

    @Override
    protected MemberVotes convert(List<String> sourceData) {
        final MemberVotes memberVotes = new MemberVotes();
        memberVotes.setMemberId(
                new MemberVotes.MemberVotesId(
                        Long.parseLong(sourceData.get(0)),
                        Long.parseLong(sourceData.get(1))
                )
        );
        memberVotes.setResult(sourceData.get(2));
        return memberVotes;
    }

    @Override
    public String getSourceName() {
        return "hlXXXXh1";
    }

    @Override
    public AgendaSource getAgendaSource() {
        return AgendaSource.VOTES;
    }
}
