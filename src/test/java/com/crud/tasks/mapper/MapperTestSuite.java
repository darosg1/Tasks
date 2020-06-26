package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTestSuite {

    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void mapToLists(){
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("1", "Name", false);
        List<TrelloListDto> listDto = new ArrayList<>();
        listDto.add(trelloListDto);
        //When
        List<TrelloList> mappingResult= trelloMapper.mapToLists(listDto);
        //Then
        Assert.assertEquals("1", mappingResult.get(0).getId());
        Assert.assertEquals("Name", mappingResult.get(0).getName());
        Assert.assertFalse(mappingResult.get(0).isClosed());
    }

    @Test
    public void mapToListsDto(){
        //Given
        TrelloList trelloList = new TrelloList("2", "Nazwa", true);
        List<TrelloList> list = new ArrayList<>();
        list.add(trelloList);
        //When
        List<TrelloListDto> mappingResult= trelloMapper.mapToListsDto(list);
        //Then
        Assert.assertEquals("2", mappingResult.get(0).getId());
        Assert.assertEquals("Nazwa", mappingResult.get(0).getName());
        Assert.assertTrue(mappingResult.get(0).isClosed());
    }

    @Test
    public void mapToBoards(){
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("100", "Lista", true);
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloListDto);

        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "Tablica 1", trelloListDtoList);
        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        trelloBoardsDto.add(trelloBoardDto);
        //When
        List<TrelloBoard> mappingResult = trelloMapper.mapToBoards(trelloBoardsDto);
        //Then
        Assert.assertEquals("1", mappingResult.get(0).getId());
        Assert.assertEquals("Tablica 1", mappingResult.get(0).getName());
        Assert.assertEquals("100", mappingResult.get(0).getLists().get(0).getId());
        Assert.assertEquals("Lista", mappingResult.get(0).getLists().get(0).getName());
        Assert.assertTrue(mappingResult.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void mapToBoardsDto(){
        //Given
        TrelloList trelloList = new TrelloList("1", "Lista", false);
        List <TrelloList> lists = new ArrayList<>();
        lists.add(trelloList);

        TrelloBoard trelloBoard = new TrelloBoard("20", "Tablica zakupów",lists);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);
        //When
        List<TrelloBoardDto> mappingResult = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        Assert.assertEquals("20", mappingResult.get(0).getId());
        Assert.assertEquals("Tablica zakupów", mappingResult.get(0).getName());
        Assert.assertEquals("1", mappingResult.get(0).getLists().get(0).getId());
        Assert.assertEquals("Lista", mappingResult.get(0).getLists().get(0).getName());
        Assert.assertFalse(mappingResult.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void mapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card", "Description", "Position", "Id");
        //When
        TrelloCard mappingResult = trelloMapper.mapToCard(trelloCardDto);
        //Then
        Assert.assertEquals("Card", mappingResult.getName());
        Assert.assertEquals("Description", mappingResult.getDescription());
        Assert.assertEquals("Position", mappingResult.getPos());
        Assert.assertEquals("Id", mappingResult.getListId());
    }

    @Test
    public void mapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Nazwa", "Opis", "Pozycja", "Id");
        //When
        TrelloCardDto mappingResult = trelloMapper.mapToCardDto(trelloCard);
        //Then
        Assert.assertEquals("Nazwa", mappingResult.getName());
        Assert.assertEquals("Opis", mappingResult.getDescription());
        Assert.assertEquals("Pozycja", mappingResult.getPos());
        Assert.assertEquals("Id", mappingResult.getListId());
    }
}


