package seedu.medibook.logic.parser;

import static seedu.medibook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.medibook.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.medibook.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.medibook.logic.parser.CliSyntax.PREFIX_MCR;
import static seedu.medibook.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.medibook.logic.commands.AddNoteCommand;
import seedu.medibook.logic.parser.exceptions.ParseException;
import seedu.medibook.model.commonfields.Date;
import seedu.medibook.model.commonfields.Name;
import seedu.medibook.model.doctor.Doctor;
import seedu.medibook.model.doctor.Mcr;
import seedu.medibook.model.medicalnote.Content;
import seedu.medibook.model.medicalnote.MedicalNote;

/**
 * Parses input arguments and creates a new AddNoteCommand object
 */
public class AddNoteCommandParser implements Parser<AddNoteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddNoteCommand
     * and returns a AddNoteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddNoteCommand parse(String args) throws ParseException {
        try {
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_NAME, PREFIX_MCR, PREFIX_CONTENT);

            if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MCR, PREFIX_CONTENT)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddNoteCommand.MESSAGE_USAGE));
            }

            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).orElse(Date.getTodayDate()));
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Mcr mcr = ParserUtil.parseMcr(argMultimap.getValue(PREFIX_MCR).get());
            Content content = ParserUtil.parseContent(argMultimap.getValue(PREFIX_CONTENT).get());

            Doctor doctor = new Doctor(name, mcr);

            MedicalNote medicalNote = new MedicalNote(date, doctor, content);

            return new AddNoteCommand(medicalNote);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(iae.getMessage());
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}