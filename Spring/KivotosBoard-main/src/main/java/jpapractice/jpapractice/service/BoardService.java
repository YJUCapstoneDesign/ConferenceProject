package jpapractice.jpapractice.service;

import jakarta.transaction.Transactional;
import jpapractice.jpapractice.customException.DataNotFoundException;
import jpapractice.jpapractice.domain.Comment;
import jpapractice.jpapractice.domain.Post;
import jpapractice.jpapractice.domain.Student;
import jpapractice.jpapractice.dto.board.CommentDto;
import jpapractice.jpapractice.dto.board.PostDto;
import jpapractice.jpapractice.dto.board.PostListDto;
import jpapractice.jpapractice.dto.board.WritePostDto;
import jpapractice.jpapractice.repository.CommentRepository;
import jpapractice.jpapractice.repository.MemberRepository;
import jpapractice.jpapractice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final PostRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;


    @Transactional
    public Long createPost(WritePostDto writePostDto) {
        Student student = memberRepository.findByAccountId(writePostDto.getWriter())
                .orElseThrow(() -> new DataNotFoundException("Student not found"));

        new Post();
        Post post = Post.builder()
                .postSubject(writePostDto.getPostSubject())
                .postContent(writePostDto.getPostContent())
                .student(student)
                .postDate(LocalDateTime.now())
                .view(0)
                .build();

        return boardRepository.save(post).getId();
    }

    @Transactional
    public Long modifyPost(WritePostDto writePostDto, Post post) {
        post.changePostSubject(writePostDto.getPostSubject());
        post.changePostContent(writePostDto.getPostContent());
        return post.getId();
    }

    @Transactional
    public Post getPost(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("사용자가 없습니다"));
    }

    @Transactional
    public CommentDto getComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(comment.getId());
        commentDto.setCommentText(comment.getCommentText());
        commentDto.setAccountId(comment.getStudent().getAccountId());
        return commentDto;
    }

    @Transactional
    public void modifyComment(String commentText, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.changeCommentText(commentText);
    }

    @Transactional
    public List<PostListDto> loadPosts(int pageNum) {
        int startPost = (pageNum - 1);
        int firstResult = startPost * 30;
        Pageable pageable = PageRequest.of(firstResult, 30); // 0은 첫 페이지를 의미하며, 30은 페이지 크기입니다.
        return boardRepository.findPosts(pageable);
    }

    @Transactional
    public Long getPageCount() {
        Long postCount = boardRepository.countPostBy();
        return (postCount / 30) + 1;
    }

    @Transactional
    public PostDto getPostAndComment(Long postId) {
        Optional<Post> optionalPost = boardRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new DataNotFoundException("사용자가 존재하지 않습니다");
        }
        Post post = optionalPost.get();
        Student student = memberRepository.findById(post.getStudent().getId()).orElseThrow();

        PostDto postDto = new PostDto();
        postDto.setPostId(post.getId());
        postDto.setPostSubject(post.getPostSubject());
        postDto.setPostContent(post.getPostContent());
        postDto.setAccountId(student.getAccountId());
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
                commentDto.setAccountId(comment.getStudent().getAccountId());
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
        Student student = memberRepository.findByAccountId(accountId)
                .orElseThrow();

        Comment comment = Comment
                .builder()
                .commentText(commentText)
                .commentDate(LocalDateTime.now())
                .student(student)
                .post(boardRepository.findById(postId).orElseThrow())
                .build();

        commentRepository.save(comment);
        return getPostAndComment(postId);
    }

    @Transactional
    public void deletePost(Long postId) {
        boardRepository.deleteById(postId);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
