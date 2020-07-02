package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

@RunWith (SpringRunner.class)
public class TrelloValidatorTest {

    @Mock
    TrelloValidator trelloValidator;

    @Test
    public void validateCardTest(){
        //Given
        TrelloCard trelloCard = new TrelloCard ("Card", "Description", "1", "Id=1");
        TrelloCard trelloCard2 = new TrelloCard ("Card2", "Description2", "2", "Id=2");
        //When
        trelloValidator.validateCard(trelloCard);
        trelloValidator.validateCard(trelloCard2);
        //Then
        verify(trelloValidator, times(1)).validateCard(trelloCard);
        verify(trelloValidator, times(1)).validateCard(trelloCard2);
    }
    @Test
    public void validateTrelloBoardsTest(){
        //Given
        TrelloList trello = new TrelloList ("1", "listname", true);
        List<TrelloList> trelloList = new ArrayList<> ();
        trelloList.add (trello);
        TrelloBoard trelloBoard = new TrelloBoard ("1", "name", trelloList);
        List<TrelloBoard> trelloBoardList = new ArrayList<> ();
        trelloBoardList.add (trelloBoard);
        TrelloValidator trelloValidator = new TrelloValidator ();
        //When
        List<TrelloBoard> validatedList = trelloValidator.validateTrelloBoards (trelloBoardList);
        //Then
        Assert.assertNotNull(validatedList);
        Assert.assertEquals(1, validatedList.size ());
        Assert.assertEquals ("1", validatedList.get (0).getId());
        Assert.assertEquals ("name", validatedList.get (0).getName());
    }
}
