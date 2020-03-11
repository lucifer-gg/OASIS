package oasis.service.search;

import oasis.bl.search.SearchService;
import oasis.data.mapping.MappingMapper;
import oasis.data.search.SearchMapper;
import oasis.entity.ArticlePO;
import oasis.parameter.search.SearchParam;
import oasis.vo.BasicResponse;
import oasis.vo.ResponseStatus;
import oasis.vo.search.ArticleDetailVO;
import oasis.vo.search.ArticleItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author L.T
 * @version 1.0
 * @date 2020/03/06 08:11
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    private MappingMapper mappingMapper;

    /**
     * 通过作者、机构、会议、研究关键词、或时间及其组合搜索检索内容
     *
     * @param searchParam
     * @return
     */
    @Override
    public BasicResponse getArtItem(SearchParam searchParam) {
        try {
            List<ArticleItemVO> articleItemVOList = new ArrayList<>();
            List<Integer> docIdList = searchMapper.getAllDocId();
            boolean reached = false;

            List<String> authors = strToList(searchParam.getAuthor());
            String authorList = listToSqlList(authors);
            if (authors.size() != 1) {
                if (searchMapper.hasTheAuthor(authorList)) {
                    docIdList.retainAll(searchMapper.getDocIdByAuthorList(authorList));
                    reached = true;
                }
            } else {
                String author = authors.get(0);
                if (!"null".equals(author)) {
                    List<Integer> theAuthorDocList = searchMapper.getDocIdByTheAuthor(author);
                    if (theAuthorDocList.size() > 0) {
                        docIdList.retainAll(theAuthorDocList);
                        reached = true;
                    }
                }
            }

            List<String> affiliations = strToList(searchParam.getAffiliation());
            String affiliationList = listToSqlList(affiliations);
            if (affiliations.size() != 1) {
                if (searchMapper.hasTheAffiliation(affiliationList)) {
                    docIdList.retainAll(searchMapper.getDocIdByAffiliationList(affiliationList));
                    reached = true;
                }
            } else {
                String affiliation = affiliations.get(0);
                if (!"null".equals(affiliation)) {
                    List<Integer> theAffiliationDocList = searchMapper.getDocIdByTheAffiliation(affiliation);
                    if (theAffiliationDocList.size() > 0) {
                        docIdList.retainAll(theAffiliationDocList);
                        reached = true;
                    }
                }
            }

            List<String> keyWords = strToList(searchParam.getKeyWords());
            String keyWordList = listToSqlList(keyWords);
            if (keyWords.size() != 1) {
                if (searchMapper.hasTheKeyWord(keyWordList)) {
                    keyWords.forEach(keyWord -> {
                        searchMapper.incKeyWordCount(keyWord);
                    });
                    docIdList.retainAll(searchMapper.getDocIdByKeyWordList(keyWordList));
                    reached = true;
                }
            } else {
                String keyWord = keyWords.get(0);
                if (!"null".equals(keyWord)) {
                    List<Integer> theKeyWordDocList = searchMapper.getDocIdByTheKeyWord(keyWord);
                    if (theKeyWordDocList.size() > 0) {
                        docIdList.retainAll(theKeyWordDocList);
                        reached = true;
                    }
                }
            }


            String publication = searchParam.getPublication();
            if (searchMapper.hasThePublication(publication)) {
                docIdList.retainAll(searchMapper.getDocIdByPublicName(publication));
                reached = true;
            }

            String startTime = searchParam.getTime().split("-")[0];
            String endTime = searchParam.getTime().split("-")[1];
            if ("0".equals(endTime)) {
                endTime = "9999";
            }
            boolean otherCons = false;
            if (!"null".equals(searchParam.getAuthor()) || !"null".equals(searchParam.getAffiliation()) || !"null".equals(searchParam.getKeyWords()) || !"null".equals(searchParam.getPublication())) {
                otherCons = true;
            }
            if (((otherCons && reached) || (!otherCons)) && searchMapper.hasThePublicYear(startTime, endTime)) {
                docIdList.retainAll(searchMapper.getDocIdByPublicYear(startTime, endTime));
                reached = true;
            }

            if (!reached) {
                docIdList = new ArrayList<>();
            }

            for (Integer tmpDocId : docIdList) {
                ArticleItemVO articleItemVO = getArtItemById(tmpDocId);
                articleItemVOList.add(articleItemVO);
            }

            return new BasicResponse<>(ResponseStatus.STATUS_SUCCESS, articleItemVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    /**
     * 通过文章 ID 检索文章详细内容
     *
     * @param docId
     * @return
     */
    @Override
    public BasicResponse getArtDetailById(int docId) {
        try {
            ArticlePO articlePO = searchMapper.getArtDetailById(docId);

            List<Integer> keyWordsIdList = mappingMapper.getKeyWordsIdList(docId);
            List<String> keyWords = getKeyWordsByIds(keyWordsIdList);
            List<Integer> authorsIdList = mappingMapper.getAuthorsIdList(docId);
            List<String> authors = getAuthorsByIds(authorsIdList);
            List<Integer> affiliationsIdList = mappingMapper.getAffiliationIdList(docId);
            List<String> affiliations = getAffiliationsByIds(affiliationsIdList);
            int publicationId = articlePO.getPublicationId();
            String publicationTitle = mappingMapper.getPublicationTitleById(publicationId);
            String publicationYear = mappingMapper.getPublicationYearById(publicationId);
            int pageNum = articlePO.getEndPage() - articlePO.getStartPage() + 1;

            ArticleDetailVO articleDetailVO = new ArticleDetailVO(docId, articlePO.getDocTitle(), articlePO.getDocAbstract(), keyWords, authors, affiliations, publicationTitle, publicationYear, articlePO.getDocIdentifier(), pageNum, articlePO.getStartPage(), articlePO.getEndPage(), articlePO.getReference());

            return new BasicResponse<>(ResponseStatus.STATUS_SUCCESS, articleDetailVO);
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    /**
     * 通过作者 ID 获取作者姓名
     *
     * @param authorsIdList
     * @return
     */
    private List<String> getAuthorsByIds(List<Integer> authorsIdList) {
        List<String> authors = new ArrayList<>();

        for (Integer authorId : authorsIdList) {
            String author = mappingMapper.getAuthorById(authorId);
            authors.add(author);
        }

        return authors;
    }

    /**
     * 通过机构 ID 获取机构名称
     *
     * @param affiliationsIdList
     * @return
     */
    private List<String> getAffiliationsByIds(List<Integer> affiliationsIdList) {
        List<String> affiliations = new ArrayList<>();

        for (Integer affiliationId : affiliationsIdList) {
            String affiliation = mappingMapper.getAffiliationById(affiliationId);
            affiliations.add(affiliation);
        }

        return affiliations;
    }

    /**
     * 通过关键字 ID 获取关键字
     *
     * @param keyWordsIdList
     * @return
     */
    private List<String> getKeyWordsByIds(List<Integer> keyWordsIdList) {
        List<String> keyWords = new ArrayList<>();

        for (Integer keyWordId : keyWordsIdList) {
            String keyWord = mappingMapper.getKeyWordById(keyWordId);
            keyWords.add(keyWord);
        }

        return keyWords;
    }

    /**
     * 通过文章 ID 获取文章条目信息
     *
     * @param docId
     * @return
     */
    private ArticleItemVO getArtItemById(int docId) {
        ArticlePO articlePO = searchMapper.getArtDetailById(docId);

        List<Integer> authorsIdList = mappingMapper.getAuthorsIdList(docId);
        List<String> authors = getAuthorsByIds(authorsIdList);
        List<Integer> affiliationIdList = mappingMapper.getAffiliationIdList(docId);
        List<String> affiliations = getAffiliationsByIds(affiliationIdList);
        List<Integer> keyWordsIdList = mappingMapper.getKeyWordsIdList(docId);
        List<String> keyWords = getKeyWordsByIds(keyWordsIdList);
        int publicationId = articlePO.getPublicationId();
        String publicationTitle = mappingMapper.getPublicationTitleById(publicationId);
        String publicationYear = mappingMapper.getPublicationYearById(publicationId);

        ArticleItemVO articleItemVO = new ArticleItemVO(docId, articlePO.getDocTitle(), affiliations, authors, publicationTitle, articlePO.getDocAbstract(), keyWords, articlePO.getPdfLink(), articlePO.getReference(), publicationYear);
        return articleItemVO;
    }

    /**
     * 字符串以"*"分割为列表
     *
     * @param str
     * @return
     */
    private List<String> strToList(String str) {

        String[] array = str.split("\\*");
        List<String> list = Arrays.asList(array);

        return list;
    }

    /**
     * 列表转 SQL 列表
     *
     * @param list
     * @return
     */
    private String listToSqlList(List<String> list) {
        StringBuilder sqlList = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sqlList.append("'").append(list.get(i)).append("'");
            if (i != list.size() - 1) {
                sqlList.append(",");
            }
        }

        return sqlList.toString();
    }

    /**
     * 通过 HashSet 剔除列表内的冗余数据
     *
     * @param list
     * @return
     */
    public static List<Integer> removeDuplicate(List<Integer> list) {
        HashSet hashSet = new HashSet(list);
        list.clear();
        list.addAll(hashSet);
        return list;
    }

}
