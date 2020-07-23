package com.fanfiction.webproject.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagsCommonRest {

    private Map<String, Integer> commonTags;
}
