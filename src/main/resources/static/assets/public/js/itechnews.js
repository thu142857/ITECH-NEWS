$(document).ready(function () {
    //setup ajax csrf
    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");
    console.log(token);
    console.log(header);
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    //auto height comment box
    $('body').on('keyup', '.comment-box', function(e) {
        let o = $(this)[0];
        //console.log(o.scrollHeight);
        o.style.height = "1px";
        o.style.height = (30 + o.scrollHeight)+"px";
    });

    $('body').on('keypress', '.comment-box', function(e) {
        let $box = $(this); //textarea
        if(e.which === 13) {
            e.preventDefault();
            let $totalOfComments = $('.total-of-comments');
            let postId = $('.post-comments').data('post-id');
            let commentContent = $box.val();
            let parentCommentId = $box.data('parent-comment-id');
            if (parentCommentId===undefined)
                parentCommentId = null;

            $.ajax({
                url: '/api/comment',
                type: 'POST',
                cache: false,
                data: {
                    content: commentContent,
                    parentCommentId: parentCommentId,
                    postId: postId
                },
                success: function(response){
                    if (parentCommentId === null) {
                        let $commentContainerClone = $('.comment-container-clone');
                        let $newCommentContainer = $commentContainerClone.clone();


                        $newCommentContainer.css('display', '');
                        $newCommentContainer.removeClass('comment-container-clone');
                        $newCommentContainer.find('p').html(response.data.content);
                        $newCommentContainer.find('span.time').html(response.data.created_at);
                        console.log($newCommentContainer.find('textarea'));
                        $newCommentContainer
                            .find('textarea')
                            .attr('data-parent-comment-id', response.data.id);

                        $commentContainerClone.after($newCommentContainer);
                        $box.val('');

                        let total = parseInt($totalOfComments.text()) + 1;
                        $totalOfComments.text(total);
                    } else {
                        let $commentReplyClone = $('.comment-reply-clone');
                        let $newReplyContainer = $commentReplyClone.clone();


                        $newReplyContainer.css('display', '');
                        $newReplyContainer.removeClass('comment-reply-clone');
                        $newReplyContainer.find('p').html(response.data.content);
                        $newReplyContainer.find('span.time').html(response.data.created_at);
                        $box.closest('.comment-container-parent')
                            .find('.comment-container-reply')
                            .before($newReplyContainer)
                            .find('textarea').val('');

                        let total = parseInt($totalOfComments.text()) + 1;
                        $totalOfComments.text(total);
                    }
                },
                error: function (){
                    console.log("ajax error");
                }
            });
        }
    });

    $('body').on('click', 'a.reply', function(e) {

        e.preventDefault();
        let $reply = $(this);
        let replyToUser = $reply.parent().find('h4').text();

        $reply
            .closest('.comment-container-parent')
            .find('.comment-container-reply')
            .css('display', 'block')
            .find('textarea')
            .val('@' + replyToUser + ' ').focus();

    });
});