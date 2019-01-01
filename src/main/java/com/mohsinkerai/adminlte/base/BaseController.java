package com.mohsinkerai.adminlte.base;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public abstract class BaseController<E extends BaseEntity> {

  private static final int PAGE_SIZE = 10;
  protected final BaseService<E> baseService;

  protected BaseController(BaseService<E> baseService) {
    this.baseService = baseService;
  }

  protected abstract String urlPath();

  protected abstract String viewPath();

  protected abstract E getEmptyObject();

  /**
   * Return Static Values for Lookup Purpose - Such as List of Departments for Employee Controller
   * List of Jamatkhana, etc.
   *
   * It is needed for individual record (add/edit)
   */
  protected abstract Map<String, Object> getAttributes();

  @GetMapping("")
  public String index() {
    return "redirect:/" + urlPath() + "/1";
  }

  @RequestMapping(value = "{pageNumber}", method = RequestMethod.GET)
  public String list(@PathVariable Integer pageNumber, Model model) {
    PageRequest pageRequest =
      new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "id");
    Page<E> page = baseService.findAll(pageRequest);

    int current = page.getNumber() + 1;
    int begin = Math.max(1, current - PAGE_SIZE);
    int end = Math.min(begin + 10, page.getTotalPages());

    model.addAttribute("urlPath", urlPath());

    model.addAttribute("list", page);
    model.addAttribute("beginIndex", begin);
    model.addAttribute("endIndex", end);
    model.addAttribute("currentIndex", current);

    return viewPath() + "/list";
  }

  @RequestMapping("add")
  public String add(Model model) {
    model.addAttribute("urlPath", urlPath());

    model.addAllAttributes(getAttributes());
    model.addAttribute("data", getEmptyObject());
    return viewPath() + "/form";
  }

  @RequestMapping("edit/{id}")
  public String edit(@PathVariable Long id, Model model) {
    model.addAttribute("urlPath", urlPath());

    model.addAllAttributes(getAttributes());
    model.addAttribute("data", baseService.findOne(id));
    return viewPath() + "/form";
  }

  @RequestMapping(value = "save", method = RequestMethod.POST)
  public String save(E e, final RedirectAttributes ra) {

    E save = baseService.save(e);
    ra.addFlashAttribute("successFlash", "Data was successfully saved.");
    return "redirect:/" + urlPath();
  }

  @RequestMapping("delete/{id}")
  public String delete(@PathVariable Long id) {

    baseService.delete(id);
    return "redirect:/" + urlPath();
  }
}
