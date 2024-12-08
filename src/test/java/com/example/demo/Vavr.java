package com.example.demo;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: fengwen.dong@going-link.com
 * @createDate: 2022-12-24 21:20
 */
public class Vavr {

    public static void main(String[] args) {
        nextPermutation(new int[]{1, 2, 3});
    }
    //输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
    //输出: [["bat"],["nat","tan"],["ate","eat","tea"]]

    public static List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> resList = new ArrayList<>();


        if (ObjectUtils.isNotEmpty(strs)) {
            List<String> strList = Arrays.asList(strs);
            final CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>(strList);
            for (int i = 0; i < list.size(); i++) {
                List<String> newList = new ArrayList<>();
                for (int j = i + 1; j < list.size(); j++) {
                    List<String> split = Arrays.asList(list.get(j).split(""));
                    int sizeSum = split.size();
                    int conSize = 0;
                    for (int i1 = 0; i1 < split.size(); i1++) {
                        if (list.get(i).contains(split.get(i1))) {
                            conSize = conSize + 1;
                        }
                    }
                    System.out.println("conSize = " + conSize);
                    System.out.println("sizeSum = " + sizeSum);
                    if (conSize == sizeSum) {
                        newList.add(list.get(j));
                        resList.add(newList);
                        list.remove(list.get(i));
                    }
                }
            }
        }
        return resList;
    }

    public static int longestConsecutive(int[] nums) {
        //输入：nums = [100,4,200,1,3,2]
        //输出：4
        //解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。
        List<Integer> list = Arrays.stream(nums).boxed().collect(Collectors.toList());
        if (list != null && list.size() < 0) {
            return 0;
        } else if (list.size() == 1) {
            return 1;
        }
        list.sort(Comparator.comparing(Integer::intValue));
        int res = 0;
        int resInt = res;
        for (int i = 1; i < list.size(); i++) {
            if ((list.get(i - 1) + 1 == list.get(i) || list.get(i - 1) == list.get(i))
                    && (list.get(i) != 0 || list.get(i - 1) < 0)) {
                if (res == 0) {
                    res = res + 2;
                } else {
                    res = res + 1;
                }
                if (resInt < res) {
                    resInt = res;
                }
            } else {
                res = 0;
            }
        }
        System.out.println(list);
        System.out.println(resInt);
        return resInt;

    }

    public static int findDuplicate(int[] nums) {
        List<Integer> collect = Arrays.stream(nums).boxed().collect(Collectors.toMap(e -> e, e -> 1, (a, b) -> a + b))
                // 获得元素出现频率的 Map，键为元素，值为元素出现的次数
                .entrySet().stream() // Set<Entry>转换为Stream<Entry>
                .filter(entry -> entry.getValue() > 1) // 过滤出元素出现次数大于 1 的 entry
                .map(entry -> entry.getKey()) // 获得 entry 的键（重复元素）对应的 Stream
                .collect(Collectors.toList()); // 转化为 List
        System.out.println(collect);
        return collect.get(0);
    }

    public static void nextPermutation(int[] nums) {
        List<String> str = new ArrayList<>();
        //[1,2,3]
        for (int i = 0; i < nums.length; i++) {
            String res = nums[i] + "";
            for (int j = 0; j < nums.length; j++) {
                if (i != j) {
                    res = res + nums[j];
                }
            }
            str.add(res);
        }
        System.out.println(str);
    }


}
