package oasis.data.rank;

import oasis.entity.rank.RankAffiliationPO;
import oasis.entity.rank.RankArticlePO;
import oasis.entity.rank.RankAuthorPO;
import oasis.entity.rank.RankKeyWordPO;
import oasis.vo.BasicResponse;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author L.T
 * @version 1.0
 * @date 2020/03/04 14:22
 */
@Repository
@Mapper
public interface RankMapper {

    /**
     * 获取论文数量前5的作者
     *
     * @return
     */
    List<RankAuthorPO> getTopAuthors();

    /**
     * 获取被引用量前5的文章
     *
     * @return
     */
    List<RankArticlePO> getTopReferences();

    /**
     * 获取搜索次数前5的关键字
     *
     * @return
     */
    List<RankKeyWordPO> getTopKeyWords();

    /**
     * 获取 ASE 会议上发表论文数量前5的机构
     *
     * @return
     */
    List<RankAffiliationPO> getAseTopAffiliations();

    /**
     * 获取 ICSE 会议上发表论文数量前5的机构
     *
     * @return
     */
    List<RankAffiliationPO> getIcseTopAffiliations();

}
