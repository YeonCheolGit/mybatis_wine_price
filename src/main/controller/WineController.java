package main.controller;

import lombok.extern.slf4j.Slf4j;
import main.paging.PageMaker;
import main.paging.SearchCriteria;
import main.service.wine.WineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequestMapping(value = "/wine")
public class WineController {
    private static final Logger logger = LoggerFactory.getLogger(WineController.class);

    private final WineService wineService;

    public WineController(WineService wineService) {
        this.wineService = wineService;
    }

    /*
     * 와인 리스트 전체 보여줌
     */
    @GetMapping(value = "/searchBarAndPagination")
    public String searchBarAndPagination(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria,
                                         Model model) {
        logger.debug("searchBar >>> ");

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCriteria(searchCriteria);
        pageMaker.setTotalCount(wineService.countWines(searchCriteria));

        model.addAttribute("allWineList", wineService.listPaging(searchCriteria));
        model.addAttribute("pageMaker", pageMaker);

        return "wine/allWineList";
    }

    /*
     * 정렬 버튼 클릭 시 동작
     * 정렬 후 페이지를 넘겨도 유지됨
     */
    @GetMapping(value = "/orderByPrice")
    public String orderByPrice(@ModelAttribute("searchCriteria") SearchCriteria searchCriteria,
                            Model model) {
        logger.debug("prices >>> ");

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCriteria(searchCriteria);
        pageMaker.setTotalCount(wineService.countWines(searchCriteria));

        boolean price = true;

        model.addAttribute("allWineList", wineService.orderByPrice(searchCriteria));
        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("orderByPrice", price);

        return "wine/allWineList";
    }

    /*
     * 검색창에서 검색 시 autocomplete 동작
     */
    @GetMapping(value = "/autocomplete")
    @ResponseBody
    public List<String> autocomplete(HttpServletRequest request) {
        logger.debug("search >>> ");
        return wineService.search(request.getParameter("term"));
    }
}
