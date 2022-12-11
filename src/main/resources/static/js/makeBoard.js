// 메인화면 페이지 로드 함수
$(document).ready(function () {
    $('#summernote').summernote({
        placeholder: '내용을 작성하세요️',
        height: 400,
        maxHeight: 400
    });
});
$('#saveButton').click(function save() {
console.log("zmfflr");
    // let formData = new FormData();
    // let title = document.getElementById("boardTitle").value;
    // let author = document.getElementById("boardAuthor").value;
    // let content = document.getElementById("summernote").value;
    // let files = document.getElementById("formFile");
    // console.log(author);
    //
    // for (var i = 0; i < files.length; i++) {
    //     formData.append("files", document.getElementsByName("file")[i].files[0]);
    // }
    //
    // formData.append("files", formData);
    // formData.append("title", title);
    // formData.append("author", author);
    // formData.append("content", content);
    // $.ajax({
    //     type: "POST",
    //     enctype: "multipart/form-data",
    //     url: "/boards",
    //     data: formData,
    //     processData: false,
    //     contentType: false,
    //     cache: false,
    //     success: function (data) {
    //         console.log("success: ", data);
    //     }
    // })
})