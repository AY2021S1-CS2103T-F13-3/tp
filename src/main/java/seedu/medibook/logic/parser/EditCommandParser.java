package seedu.medibook.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.medibook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.medibook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.medibook.logic.parser.CliSyntax.PREFIX_BLOOD_TYPE;
import static seedu.medibook.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.medibook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.medibook.logic.parser.CliSyntax.PREFIX_HEIGHT;
import static seedu.medibook.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.medibook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.medibook.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.medibook.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.medibook.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.medibook.commons.core.index.Index;
import seedu.medibook.logic.commands.EditCommand;
import seedu.medibook.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.medibook.logic.parser.exceptions.ParseException;
import seedu.medibook.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_IC, PREFIX_NAME, PREFIX_DATE, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_HEIGHT, PREFIX_WEIGHT, PREFIX_BLOOD_TYPE, PREFIX_TAG);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditPatientDescriptor editPatientDescriptor = new EditPatientDescriptor();
        if (argMultimap.getValue(PREFIX_IC).isPresent()) {
            editPatientDescriptor.setIc(ParserUtil.parseIc(argMultimap.getValue(PREFIX_IC).get()));
        }
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPatientDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editPatientDescriptor.setDateOfBirth(ParserUtil.parseDateOfBirth(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPatientDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPatientDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get());
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPatientDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS)).get());
        }
        if (argMultimap.getValue(PREFIX_HEIGHT).isPresent()) {
            editPatientDescriptor.setHeight(ParserUtil.parseHeight(argMultimap.getValue(PREFIX_HEIGHT)).get());
        }
        if (argMultimap.getValue(PREFIX_WEIGHT).isPresent()) {
            editPatientDescriptor.setWeight(ParserUtil.parseWeight(argMultimap.getValue(PREFIX_WEIGHT)).get());
        }
        if (argMultimap.getValue(PREFIX_BLOOD_TYPE).isPresent()) {
            editPatientDescriptor.setBloodType(ParserUtil
                    .parseBloodType(argMultimap.getValue(PREFIX_BLOOD_TYPE)).get());
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPatientDescriptor::setTags);

        if (!editPatientDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editPatientDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
