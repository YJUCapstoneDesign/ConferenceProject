package jpapractice.jpapractice.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jpapractice.jpapractice.customException.DataNotFoundException;
import jpapractice.jpapractice.domain.Account;
import jpapractice.jpapractice.domain.Comment;
import jpapractice.jpapractice.domain.Post;
import jpapractice.jpapractice.domain.Student;
import jpapractice.jpapractice.dto.board.CommentDto;
import jpapractice.jpapractice.dto.board.PostDto;
import jpapractice.jpapractice.dto.board.PostListDto;
import jpapractice.jpapractice.dto.board.WritePostDto;
import jpapractice.jpapractice.repository.BoardRepository;
import jpapractice.jpapractice.repository.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  private final BoardRepository boardRepository;
  private final InformationRepository informationRepository;

  @Autowired
  public BoardService(BoardRepository boardRepository,
      InformationRepository informationRepository) {
    this.boardRepository = boardRepository;
    this.informationRepository = informationRepository;
  }

  @Transactional
  public Long createPost(WritePostDto writePostDto) {
    Student student = informationRepository.getAccountById(
        writePostDto.getWriter()).getStudent();
    Account account = informationRepository.getAccountReference(
        writePostDto.getWriter());
    new Post();
    Post post = Post.builder()
                    .subject(writePostDto.getPostSubject())
                    .postContent(writePostDto.getPostContent())
                    .account(account)
                    .student(student)
                    .postDate(LocalDateTime.now())
                    .view(0)
                    .build();

    return boardRepository.savePost(post).getId();
  }

  @Transactional
  public Long modifyPost(WritePostDto writePostDto, Post post) {
    post.changePostSubject(writePostDto.getPostSubject());
    post.changePostContent(writePostDto.getPostContent());
    return post.getId();
  }

  @Transactional
  public Post getPost(Long id) {

    Post post = boardRepository.getPost(id).orElseThrow(
        () -> new DataNotFoundException("사용자가 없습니다"));
    return post;
//    Optional<Post> optionalPost = boardRepository.getPost(id);
//    if (optionalPost.isEmpty()) {
//      throw new DataNotFoundException("사용자가 존재하지 않습니다");
//    } else {
//      return optionalPost.get();
//    }
  }

  @Transactional
  public CommentDto getComment(Long id) {
    Comment comment = boardRepository.getComment(id);
    CommentDto commentDto = new CommentDto();
    commentDto.setCommentId(comment.getId());
    commentDto.setCommentText(comment.getCommentText());
    commentDto.setAccountId(comment.getAccount().getId());
    return commentDto;
  }

  @Transactional
  public void modifyComment(String commentText, Long commentId) {
    Comment comment = boardRepository.getComment(commentId);
    comment.changeCommentText(commentText);
  }

  @Transactional
  public List<PostListDto> loadPosts(int pageNum) {
    int startPost = pageNum - 1;
    return boardRepository.findPosts(startPost);
  }

  @Transactional
  public Long getPageCount() {
    Long postCount = boardRepository.countAllPost();
    Long pageCount = (postCount / 30) + 1;
    return pageCount;
  }

  @Transactional
  public PostDto getPostAndComment(Long postId) {
    Optional<Post> optionalPost = boardRepository.getPost(postId);
    if (optionalPost.isEmpty()) {
      throw new DataNotFoundException("사용자가 존재하지 않습니다");
    }
    Post post = optionalPost.get();
    Account account = informationRepository.getAccountById(
        post.getAccount().getId());
    System.out.println(account.getId());

    PostDto postDto = new PostDto();
    postDto.setPostId(post.getId());
    postDto.setPostSubject(post.getPostSubject());
    postDto.setPostContent(post.getPostContent());
    postDto.setAccountId(account.getId());
    postDto.setPostDate(post.getPostDate());
    postDto.setView(post.getView());
    postDto.setStudentName(post.getStudent().getName());

    List<Comment> comments = post.getComments();
    if (comments.isEmpty()) {
      return postDto;
    } else {
      List<CommentDto> commentDtos = new ArrayList<>();
      for (int i = 0; i < comments.size(); i++) {
        Comment comment = comments.get(i);
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(comment.getId());
        commentDto.setCommentText(comment.getCommentText());
        commentDto.setAccountId(comment.getAccount().getId());
        commentDto.setStudentName(comment.getStudent().getName());
        commentDto.setCommentDate(comment.getCommentDate());
        commentDtos.add(commentDto);
      }

      postDto.setComments(commentDtos);
      return postDto;
    }

  }

  @Transactional
  public PostDto saveComment(String commentText, String accountId,
      Long postId) {
    Account account = informationRepository.getAccountById(accountId);
    Student student = account.getStudent();
    
    Comment comment = Comment
        .builder()
        .commentText(commentText)
        .commentDate(LocalDateTime.now())
        .account(account)
        .student(student)
        .post(informationRepository.getPostReference(postId))
        .build();

    boardRepository.saveComment(comment);
    return getPostAndComment(postId);
  }

  @Transactional
  public void deletePost(Long postId) {
    Post post = informationRepository.getPostReference(postId);
    boardRepository.removePost(post);
  }

  @Transactional
  public void deleteComment(Long commentId) {
    Comment comment = informationRepository.getCommentReference(commentId);
    boardRepository.removeComment(comment);
  }
}
