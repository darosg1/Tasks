package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TrelloServiceTest {
    @InjectMocks
    TrelloService trelloService;

    @Mock
    TrelloClient trelloClient;

    @Mock
    AdminConfig adminConfig;

    @Mock
    SimpleEmailService simpleEmailService;

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<> ();
        trelloLists.add ( new TrelloListDto ( "1", "my_list", false ) );
        List<TrelloBoardDto> trelloBoards = new ArrayList<> ();
        trelloBoards.add ( new TrelloBoardDto ( "1", "my_task", trelloLists ) );
        when ( trelloClient.getTrelloBoards () ).thenReturn ( trelloBoards );
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloService.fetchTrelloBoards ();
        //Then
        Assert.assertNotNull ( trelloBoardDtos );
        Assert.assertEquals ( 1, trelloBoardDtos.size () );

        trelloBoardDtos.forEach ( trelloBoardDto -> {
            Assert.assertEquals ( "1", trelloBoardDto.getId () );
            Assert.assertEquals ( "my_task", trelloBoardDto.getName () );


            trelloBoardDto.getLists ().forEach ( trelloListDto -> {
                Assert.assertEquals ( "1", trelloListDto.getId () );
                Assert.assertEquals ( "my_list", trelloListDto.getName () );
                Assert.assertFalse ( trelloListDto.isClosed () );
            } );
        } );
    }

    @Test
    public void createTrelloCardTest() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto ( "name", "description", "position", "listId" );
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto ( "idDto", "nameDto", "shortUrl", new Badges () );
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("admin@test.com");
        //When
        CreatedTrelloCardDto newCard = trelloService.createTrelloCard(trelloCardDto);
        //Then
        Assert.assertEquals("idDto", newCard.getId());
        Assert.assertEquals("nameDto", newCard.getName());
        Assert.assertEquals("shortUrl", newCard.getShortUrl());
    }
}
