package oasis.vo.search;

import java.util.List;

/**
 * @author L.T
 * @version 1.0
 * @date 2020/03/04 08:26
 * @description 文章详细信息 VO 类
 */
public class ArticleDetailVO {

    /**
     * 文章编号
     */
    private Integer docId;

    /**
     * 文章标题
     */
    private String docTitle;

    /**
     * 文章摘要
     */
    private String docAbstract;

    /**
     * 关键词
     */
    private List<String> keyWords;

    /**
     * 作者（们）
     */
    private List<String> authors;

    /**
     * 机构（们）【机构与作者一一对应】
     */
    private List<String> affiliation;

    /**
     * 会议/期刊信息名称
     */
    private String publicationTitle;

    /**
     * 会议/期刊时间（年份）
     */
    private String publicationTime;

    /**
     * 标识符
     */
    private String docIdentifier;

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 开始页
     */
    private Integer startPage;

    /**
     * 结束页
     */
    private Integer endPage;

    /**
     * 文章被引用数
     */
    private Integer refs;

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getDocAbstract() {
        return docAbstract;
    }

    public void setDocAbstract(String docAbstract) {
        this.docAbstract = docAbstract;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public List<String> getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(List<String> affiliation) {
        this.affiliation = affiliation;
    }

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public void setPublicationTitle(String publicationTitle) {
        this.publicationTitle = publicationTitle;
    }

    public String getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(String publicationTime) {
        this.publicationTime = publicationTime;
    }

    public String getDocIdentifier() {
        return docIdentifier;
    }

    public void setDocIdentifier(String docIdentifier) {
        this.docIdentifier = docIdentifier;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }

    public Integer getRefs() {
        return refs;
    }

    public void setRefs(Integer refs) {
        this.refs = refs;
    }

    public ArticleDetailVO() {
    }

    public ArticleDetailVO(Integer docId, String docTitle, String docAbstract, List<String> keyWords, List<String> authors, List<String> affiliation, String publicationTitle, String publicationTime, String docIdentifier, Integer pageNum, Integer startPage, Integer endPage, Integer refs) {
        this.docId = docId;
        this.docTitle = docTitle;
        this.docAbstract = docAbstract;
        this.keyWords = keyWords;
        this.authors = authors;
        this.affiliation = affiliation;
        this.publicationTitle = publicationTitle;
        this.publicationTime = publicationTime;
        this.docIdentifier = docIdentifier;
        this.pageNum = pageNum;
        this.startPage = startPage;
        this.endPage = endPage;
        this.refs = refs;
    }

}
