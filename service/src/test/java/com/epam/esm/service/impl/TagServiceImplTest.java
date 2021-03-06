package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    @Mock
    private TagDao tagDao = Mockito.mock(TagDao.class);

    @InjectMocks
    private TagServiceImpl tagService;

    private static final Tag TAG_1 = new Tag(1, "tagName1");
    private static final Tag TAG_2 = new Tag(2, "tagName3");
    private static final Tag TAG_3 = new Tag(3, "tagName5");
    private static final Tag TAG_4 = new Tag(4, "tagName4");
    private static final Tag TAG_5 = new Tag(5, "tagName2");
    private static final Tag TAG_FOR_INSERT = new Tag("tagName");

    private static final String SORT_PARAMETER = "ASC";
    private static final int PAGE = 0;
    private static final int SIZE = 5;

    @Test
    public void testGetById() {
        when(tagDao.findById(TAG_3.getId())).thenReturn(Optional.of(TAG_3));

        Tag actual = tagService.getById(TAG_3.getId());
        Tag expected = TAG_3;

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAll() {
        List<Tag> tags = Arrays.asList(TAG_1, TAG_2, TAG_3, TAG_4, TAG_5);
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        when(tagDao.findAll(pageRequest).toList()).thenReturn(tags);

        List<Tag> actual = tagService.getAll(PAGE, SIZE);
        List<Tag> expected = tags;

        assertEquals(expected, actual);
    }

    @Test
    public void testInsert() {
        when(tagDao.save(TAG_FOR_INSERT)).thenReturn(TAG_FOR_INSERT);

        Tag actual = tagService.insert(TAG_FOR_INSERT);
        Tag expected = TAG_FOR_INSERT;

        assertEquals(expected, actual);
    }

    @Test
    public void testDoFilter() {
        List<Tag> tags = Arrays.asList(TAG_1, TAG_5, TAG_2, TAG_4, TAG_3);
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("sortByTagName", SORT_PARAMETER);
        Pageable pageRequest = PageRequest.of(PAGE, SIZE);
        when(tagDao.findWithFilters(requestParams, pageRequest)).thenReturn(tags);

        List<Tag> actual = tagService.doFilter(requestParams, PAGE, SIZE);
        List<Tag> expected = tags;

        assertEquals(expected, actual);
    }

    @Test
    public void testGetMostPopularTagOfUserWithHighestCostOfAllOrders() {
        when(tagDao.findMostPopularTagOfUserWithHighestCostOfAllOrders()).thenReturn(Optional.of(TAG_1));

        Tag actual = tagService.getMostPopularTagOfUserWithHighestCostOfAllOrders();
        Tag expected = TAG_1;

        assertEquals(expected, actual);
    }
}