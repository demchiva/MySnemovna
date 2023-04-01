package cz.my.snemovna.service.loader;

import cz.my.snemovna.service.parsers.members.MembersAgendaParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The source loader for members agenda.
 */
@Component
public class MembersSourceLoader extends AbstractSourceLoader {

    public static final String DIR_NAME = "members";
    private final MembersAgendaParser membersAgendaParser;

    public MembersSourceLoader(@Value("${snemovna.url.members}") String dataSourceUrl,
                               MembersAgendaParser membersAgendaParser) {
        super(dataSourceUrl);
        this.membersAgendaParser = membersAgendaParser;
    }

    @Override
    public String getDirectoryName() {
        return DIR_NAME;
    }

    @Override
    public void fill() {
        membersAgendaParser.parseAndSave(getDirectoryName());
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
