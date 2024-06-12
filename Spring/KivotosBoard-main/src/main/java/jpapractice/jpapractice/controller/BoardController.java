package jpapractice.jpapractice.controller;

import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import jpapractice.jpapractice.domain.Post;
import jpapractice.jpapractice.dto.board.CommentDto;
import jpapractice.jpapractice.dto.board.PostDto;
import jpapractice.jpapractice.dto.board.PostListDto;
import jpapractice.jpapractice.dto.board.WritePostDto;
import jpapractice.jpapractice.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/post")
@AllArgsConstructor
@PreAuthorize("isAuthenticated()") //class 단위로도 설정 가능함
public class BoardController {

  private final BoardService boardService;

  @GetMapping("/list/{pageNum}")
  public String list(@PathVariable int pageNum, Model model,
      Principal principal) {
    List<PostListDto> list = boardService.loadPosts(pageNum);
    model.addAttribute("list", list);
    model.addAttribute("pageNum", pageNum);
    model.addAttribute("pageCount", boardService.getPageCount());
    return "board/list";
  }

  @GetMapping("/create")
  public String create(WritePostDto post, Model model) {

    model.addAttribute("post", post);
    return "board/writePost";
  }

  @PostMapping("/create")
  public String create(@Valid WritePostDto writePostDto,
      BindingResult bindingResult, Principal principal,
      RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {
      return "board/writePost";
    }
    writePostDto.setWriter(principal.getName());
    Long postId = boardService.createPost(writePostDto);
    redirectAttributes.addAttribute("postId", postId);
    return "redirect:{postId}";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("{postId}/modify")
  public String modifyPost(@PathVariable("postId") Long id,
      Principal principal, WritePostDto post, Model model) {
    PostDto postData = boardService.getPostAndComment(id);
    model.addAttribute("post", postData);
    return "board/writePost";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("{postId}/modify")
  public String modifyPost(@Valid WritePostDto modifyPost,
      BindingResult bindingResult, Principal principal,
      @PathVariable("postId") Long postId,
      RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      return "board/writePost";
    }
    Post post = boardService.getPost(postId);
    if (!post.getAccount().getId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "수정 권한이 없습니다");
    }
    Long post_id = boardService.modifyPost(modifyPost, post);
    redirectAttributes.addAttribute("postId", postId);
    return "redirect:/post/{postId}";
  }

  @GetMapping("/{postId}")
  public String post(@PathVariable("postId") Long postId, Model model) {
    PostDto postDto = boardService.getPostAndComment(postId);
    model.addAttribute("postDto", postDto);
    return "board/post";
  }

  @PostMapping("{postId}")
  public String editPost(@PathVariable("postId") Long postId,
      RedirectAttributes redirectAttributes, Principal principal) {
    redirectAttributes.addAttribute("postId", postId);
    return "redirect:post/{postId}";
  }

  @PostMapping("/{postId}/comment/create")
  public String commentCreate(@RequestParam String comment,
      @PathVariable("postId") Long postId, Principal principal,
      Model model) {

    PostDto postDto = boardService.saveComment(comment,
        principal.getName(), postId);
    model.addAttribute("postDto", postDto);

    return "redirect:/post/{postId}";
  }

  @GetMapping("/{postId}/comment/{commentId}/modify")
  public String modifyComment(@PathVariable("postId") Long postId,
      @PathVariable("commentId") Long commentId, Model model) {
    CommentDto commentDto = boardService.getComment(commentId);
    model.addAttribute("postId", postId);
    model.addAttribute("commentDto", commentDto);
    return "board/modifyComment";
  }

  @PostMapping("/{postId}/comment/{commentId}/modify")
  public String modifyComment(@PathVariable("postId") Long postId,
      @PathVariable("commentId") Long commentId,
      @RequestParam String comment, Principal principal,
      RedirectAttributes redirectAttributes) {
    CommentDto commentDto = boardService.getComment(commentId);
    if (!commentDto.getAccountId().equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "사용자가 일치하지 않습니다.");
    }
    boardService.modifyComment(comment, commentId);
    redirectAttributes.addAttribute("postId", postId);
    return "redirect:/post/{postId}";
  }

  @PostMapping("/{postId}/remove")
  public String removePost(@PathVariable("postId") Long postId,
      @RequestParam String accountId, Principal principal) {
    if (!accountId.equals(principal.getName())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "삭제 권한이 없습니다");
    }
    boardService.deletePost(postId);
    return "redirect:/post/list/1";
  }

  @PostMapping("/{postId}/comment/{commentId}/remove")
  public String removeComment(@PathVariable Long postId,
      @PathVariable Long commentId,
      RedirectAttributes redirectAttributes) {
    boardService.deleteComment(commentId);
    redirectAttributes.addAttribute("postId", postId);
    return "redirect:/post/{postId}";
  }


}
