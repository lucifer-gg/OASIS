package oasis.data.mapping;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author L.T
 * @version 1.0
 * @date 2020/03/06 08:53
 */
@Repository
@Mapper
public interface MappingMapper {

    /**
     * 根据文章编号检索关键字 ID 列表
     *
     * @param docId
     * @return
     */
    List<Integer> getKeyWordsIdList(@Param("docId") int docId);

    /**
     * 根据文章编号检索作者 ID 列表
     *
     * @param docId
     * @return
     */
    List<Integer> getAuthorsIdList(@Param("docId") int docId);

    /**
     * 根据文章编号检索机构 ID 列表
     *
     * @param docId
     * @return
     */
    List<Integer> getAffiliationIdList(@Param("docId") int docId);

    /**
     * 根据作者 ID 检索文章编号
     *
     * @param authorId
     * @return
     */
    List<Integer> getArtIdByAuthorId(@Param("authorId") int authorId);

    /**
     * 根据机构 ID 检索文章编号
     *
     * @param affiliationId
     * @return
     */
    List<Integer> getArtIdByAffiliationId(@Param("affiliationId") int affiliationId);

    /**
     * 根据关键字 ID 获取关键字
     *
     * @param keyWordId
     * @return
     */
    String getKeyWordById(@Param("keyWordId") Integer keyWordId);

    /**
     * 根据作者 ID 获取作者姓名
     *
     * @param authorId
     * @return
     */
    String getAuthorById(@Param("authorId") Integer authorId);

    /**
     * 根据机构 ID 获取机构名称
     *
     * @param affiliationId
     * @return
     */
    String getAffiliationById(@Param("affiliationId") Integer affiliationId);

    /**
     * 根据会议/期刊信息编号获取其名称
     *
     * @param publicationId
     * @return
     */
    String getPublicationTitleById(@Param("publicationId") Integer publicationId);

    /**
     * 根据会议/期刊信息编号获取其年份
     *
     * @param publicationId
     * @return
     */
    String getPublicationYearById(@Param("publicationId") Integer publicationId);

}
