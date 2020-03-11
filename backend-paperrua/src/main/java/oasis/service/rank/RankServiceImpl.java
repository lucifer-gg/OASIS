package oasis.service.rank;

import oasis.bl.rank.RankService;
import oasis.data.rank.RankMapper;
import oasis.entity.rank.*;
import oasis.vo.BasicResponse;
import oasis.vo.ResponseStatus;
import oasis.vo.rank.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author L.T
 * @version 1.0
 * @date 2020/03/04 14:21
 */
@Service
public class RankServiceImpl implements RankService {

    @Autowired
    private RankMapper rankMapper;

    /**
     * 获取论文数量前5的作者
     *
     * @return
     */
    @Override
    public BasicResponse getTopAuthors() {
        try {
            List<RankAuthorPO> topAuthorsPO = rankMapper.getTopAuthors();

            List<AuthorVO> topAuthorsVO = new ArrayList<>();
            for (RankAuthorPO topAuthorPO : topAuthorsPO) {
                AuthorVO authorVO = new AuthorVO(topAuthorPO.getAuthorName(), topAuthorPO.getAuthorId(), topAuthorPO.getArticleNum());
                topAuthorsVO.add(authorVO);
            }

            return new BasicResponse<>(ResponseStatus.STATUS_SUCCESS, topAuthorsVO);
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    /**
     * 获取被引用量前5的文章
     *
     * @return
     */
    @Override
    public BasicResponse getTopReferences() {
        try {
            List<RankArticlePO> topArticlesPO = rankMapper.getTopReferences();

            List<ArticleVO> topArticlesVO = new ArrayList<>();
            topArticlesPO.stream().forEach(topArticlePO -> {
                ArticleVO articleVO = new ArticleVO(topArticlePO.getDocTitle(), topArticlePO.getDocId(), topArticlePO.getReference());
                topArticlesVO.add(articleVO);
            });

            return new BasicResponse<>(ResponseStatus.STATUS_SUCCESS, topArticlesVO);
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    /**
     * 获取搜索次数前5的关键字
     *
     * @return
     */
    @Override
    public BasicResponse getTopKeyWords() {
        try {
            List<RankKeyWordPO> topKeyWordsPO = rankMapper.getTopKeyWords();

            List<KeyWordsVO> topKeyWordsVO = new ArrayList<>();
            topKeyWordsPO.stream().forEach(topKeyWordPO -> {
                KeyWordsVO keyWordsVO = new KeyWordsVO(topKeyWordPO.getKeyWordsId(), topKeyWordPO.getKeyWords(), topKeyWordPO.getKeyWordsSearchTime());
                topKeyWordsVO.add(keyWordsVO);
            });

            return new BasicResponse<>(ResponseStatus.STATUS_SUCCESS, topKeyWordsVO);
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    /**
     * 获取 ASE 会议上发表论文数量前5的机构
     *
     * @return
     */
    @Override
    public BasicResponse getAseTopAffiliations() {
        try {
            List<RankAffiliationPO> topAffiliationsPO = rankMapper.getAseTopAffiliations();

            List<AffiliationVO> topAffiliationsVO = new ArrayList<>();
            topAffiliationsPO.stream().forEach(topAffiliationPO -> {
                AffiliationVO affiliationVO = new AffiliationVO(topAffiliationPO.getAffiliationName(), topAffiliationPO.getAffiliationId(), topAffiliationPO.getArticleNum());
                topAffiliationsVO.add(affiliationVO);
            });

            return new BasicResponse<>(ResponseStatus.STATUS_SUCCESS, topAffiliationsVO);
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

    /**
     * 获取 ICSE 会议上发表论文数量前5的机构
     *
     * @return
     */
    @Override
    public BasicResponse getIcseTopAffiliations() {
        try {
            List<RankAffiliationPO> topAffiliationsPO = rankMapper.getIcseTopAffiliations();

            List<AffiliationVO> topAffiliationsVO = new ArrayList<>();
            topAffiliationsPO.stream().forEach(topAffiliationPO -> {
                AffiliationVO affiliationVO = new AffiliationVO(topAffiliationPO.getAffiliationName(), topAffiliationPO.getAffiliationId(), topAffiliationPO.getArticleNum());
                topAffiliationsVO.add(affiliationVO);
            });

            return new BasicResponse<>(ResponseStatus.STATUS_SUCCESS, topAffiliationsVO);
        } catch (Exception e) {
            e.printStackTrace();
            return new BasicResponse(ResponseStatus.SERVER_ERROR);
        }
    }

}
