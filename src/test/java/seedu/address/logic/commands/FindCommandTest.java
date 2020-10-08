package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PATIENT_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BENSON;
import static seedu.address.testutil.TypicalPatients.CARL;
import static seedu.address.testutil.TypicalPatients.ELLE;
import static seedu.address.testutil.TypicalPatients.FIONA;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.FieldContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        FieldContainsKeywordsPredicate firstPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("first"), PREFIX_DOB);
        FieldContainsKeywordsPredicate secondPredicate =
                new FieldContainsKeywordsPredicate(Collections.singletonList("second"), PREFIX_IC);

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPatientFound() {
        String expectedMessage = String.format(MESSAGE_PATIENT_LISTED_OVERVIEW, 0);
        FieldContainsKeywordsPredicate predicate = prepareNamePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPatientList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPatientList());
    }

    @Test
    public void execute_multipleKeywordsSingleField_multiplePatientsFound() {
        // name field
        String expectedMessage = String.format(MESSAGE_PATIENT_LISTED_OVERVIEW, 3);
        FieldContainsKeywordsPredicate predicate = prepareNamePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPatientList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPatientList());

        // ic field
        expectedMessage = String.format(MESSAGE_PATIENT_LISTED_OVERVIEW, 2);
        predicate = prepareIcPredicate("S9234567A F7654321Q");
        command = new FindCommand(predicate);
        expectedModel.updateFilteredPatientList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, ELLE), model.getFilteredPatientList());
    }

    @Test
    public void execute_multipleKeywordsMultipleFields_multiplePatientsFound() {
        // name field
        String expectedMessage = String.format(MESSAGE_PATIENT_LISTED_OVERVIEW, 3);
        FieldContainsKeywordsPredicate predicate1 = prepareNamePredicate("Kurz Elle Kunz Pauline");
        FieldContainsKeywordsPredicate predicate2 = prepareIcPredicate("S9876543W F7654321Q S9777777R");
        FindCommand command = new FindCommand(Arrays.asList(predicate1, predicate2));
        expectedModel.updateFilteredPatientList(predicate1.and(predicate2));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, CARL, ELLE), model.getFilteredPatientList());
    }

    /**
     * Parses {@code userInput} into a {@code FieldContainsKeywordsPredicate} for the name field.
     */
    private FieldContainsKeywordsPredicate prepareNamePredicate(String userInput) {
        return new FieldContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")), PREFIX_NAME);
    }

    /**
     * Parses {@code userInput} into a {@code FieldContainsKeywordsPredicate} for the ic field.
     */
    private FieldContainsKeywordsPredicate prepareIcPredicate(String userInput) {
        return new FieldContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")), PREFIX_IC);
    }
}
