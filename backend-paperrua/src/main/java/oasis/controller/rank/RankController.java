package oasis.controller.rank;

import oasis.bl.rank.RankService;
import oasis.vo.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author L.T
 * @version 1.0
 * @date 2020/03/04 14:16
 * @description 排序控制器
 */
@CrossOrigin
@RestController
@RequestMapping("/rank")
public class RankController {

    @Autowired
    private RankService rankService;

    @Autowired
    public RankController(RankService rankService){
        this.rankService=rankService;
    }

    @GetMapping("/topAuthor")
    public BasicResponse getTopAuthor() {
        return rankService.getTopAuthors();
    }

    @GetMapping("/topReference")
    public BasicResponse getTopReference() {
        return rankService.getTopReferences();
    }

    @GetMapping("/topKeyWords")
    public BasicResponse getTopKeyWords() {
        return rankService.getTopKeyWords();
    }

    @GetMapping("/aseTopAff")
    public BasicResponse getAseTopAff() {
        return rankService.getAseTopAffiliations();
    }

    @GetMapping("/icseTopAff")
    public BasicResponse getIcseTopAff() {
        return rankService.getIcseTopAffiliations();
    }

}
