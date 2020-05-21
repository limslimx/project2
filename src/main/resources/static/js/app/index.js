var validity = {
    "uId":false,
    "email":false,
    "pwCheck":false
};

function validResult(id, msg, valid, focus=false) {
    $("#" + id + "_msg").text(msg);
    $("#" + id + "_msg").attr("style", "color:" + (valid ? "green" : "red"));
    validity[id] = valid;
    console.log(validity["uId"]);
    console.log(validity["email"]);
    console.log(validity["pwCheck"]);
    if(focus) {
        $("#" + id).focus();
    }
    validCheckAll();
};

function validCheckAll() {
    if (validity["uId"] && validity["email"] && validity["pwCheck"]) {
        console.log("success");
        $("#btn-signup").removeAttr("disabled");
    } else {
        console.log("fail");
        $("#btn-signup").attr("disabled", "disabled");
    }
};

var signup = {
    init: function () {
        var _this = this;

        $("#uId").on("input", function () {
            var uId = $("#uId").val();
            var idFormat= /\W/g;
            var msg = "";
            if (uId == "") {
                msg = "아이디를 입력해주세요.";
            } else if (idFormat.test(uId)) {
                msg = "영문, 숫자, 언더바만 사용가능합니다.";
            } else {
                _this.validateUId();
            }
            validResult("uId", msg, false);
        });

        $("#email").on("input", function () {
            var email = $("#email").val();
            var emailFormat = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
            var msg = "";
            if (email == "") {
                msg = "이메일을 입력해주세요.";
            } else if (!emailFormat.test(email)) {
                msg = "유효하지 않은 이메일입니다.";
            } else {
                _this.validateEmail();
            }
            validResult("email", msg, false);
        });

        $("#password").on("input", function () {
            _this.validatePw();
        });

        $("#passwordCheck").on("input", function () {
            _this.validatePw();
        });

    },
    validateUId: function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var valid = false;
        var msg = "";
        var uId = $("#uId").val();

        $.ajax({
            type: "post",
            url: "/member/signup/checkId",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: uId,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                console.log(result);
                if (result == 0) {
                    msg = "이미 사용중인 아이디입니다.";
                    $("#uId").focus();
                } else {
                    msg = "사용 가능한 아이디입니다.";
                    valid = true;
                }
                validResult("uId", msg, valid, !valid);
            },
            fail: function (error) {
                alert(error);
            }
        });
    },
    validateEmail: function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var valid = false;
        var msg = "";
        var email = $("#email").val();

        $.ajax({
            type: "post",
            url: "/member/signup/checkEmail",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: email,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                if (result == 0) {
                    msg = "이미 등록된 이메일입니다.";
                    $("#email").focus();
                } else {
                    msg = "사용 가능한 이메일입니다.";
                    valid = true;
                }
                validResult("email", msg, valid, !valid);
            },
            fail: function (error) {
                alert(error);
            }
        });
    },
    validatePw: function () {
        var pw = $("#password").val();
        var pwCheck = $("#passwordCheck").val();
        var pwFormat = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$/;
        var valid = false;
        var msg = "";


        if (!pwFormat.test(pw)) {
            msg = "비밀번호 형식에 맞게 입력해주세요.";
        } else if (pw != pwCheck) {
            msg = "암호가 일치하지 않습니다.";
        } else {
            msg = "암호가 일치합니다.";
            valid = true;
        }
        validResult("pwCheck", msg, valid)
    }

};
var findId = {
    init: function () {
        $("#btn-findId").on("click", function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var emailFormat = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
            var email = $("#email").val();

            if (!emailFormat.test(email)) {
                window.alert("이메일 형식에 맞게 작성해주세요");
            } else{
                $.ajax({
                    type: "post",
                    url: "/member/findId",
                    contentType: "application/json; charset=utf-8",
                    data: email,
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(header, token);
                    }
                }).done(function (result) {
                    if (result == "null") {
                        alert("입력하신 이메일에 해당하는 아이디가 존재하지 않습니다.");
                    } else {
                        alert("찾으시는 아이디는 "+result+"입니다.");
                        window.location.href = "/member/login";
                    }
                }).fail(function (error) {
                    console.log(JSON.stringify(error));
                });
            }
        });
    }
};

var bookSearch={
    init :function () {
        var _this = this;

        $('#btn-search').on('click', function () {
            _this.search();
        });
    },
    search: function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var searchBy = $('#search_info').val();

        $.ajax({
            type: 'post',
            url: '/book/info',
            dataType: "json",
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(searchBy),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (json) {
                var msg="";
                for (var i = 0; i < json.length; i++) {
                    var name = json[i].name;
                    var author = json[i].author;
                    msg += ("<div id="+json[i].id+"></div>")
                    msg += ("<div id=book"+i+">")
                    msg += ("<img src=" + json[i].img + "></img><br/>");
                    msg += ("<h4 id=title"+i+">" + json[i].name + "</h4>");
                    if (!(json[i].subName == "")) {
                        msg += ("소제목: <h6 id=subTitle" + i + ">" + json[i].subName + "</h6>");
                    }
                    msg += ("저자: <h6 id=author"+i+">"+ json[i].author + "</h6>");
                    msg += ("카테고리: <h6 id=category"+i+">" + json[i].detailCategory + "</h6>");
                    if (!(json[i].rank == "")) {
                        msg += ("순위: " + json[i].rank + "<br/>");
                    }
                    msg += ("태그: " + json[i].tag + "<br/>");
                    msg += ("출간일: " + json[i].publicationDate.substring(0, json[i].publicationDate.length-2) + "<br/>");
                    msg += ("<button type=button class='btn btn-sm btn-success' id='btn-review"+json[i].id+"' onclick='bookReview.init(this.id)'>독서록 작성하기</button><br/>");
                    msg += ("</div>")
                    msg+=("<hr/>");
                }
                $('#book_info').html(msg);
            },
            fail: function (error) {
                alert(JSON.stringify(error));
            }
        });
    }
};

var bookReview = {
    init: function(id) {
        var realId = id.substring(10, id.length);

        window.location.href = "/bookReview/"+realId;
    },
    saveReview: function () {
        $("#btn-reviewSave").on("click", function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var data = {
                id: $("#id").val(),
                name: $("#name").val(),
                author: $("#author").val(),
                category: $("#category").val(),
                title: $("#title").val(),
                content: $("#content").val()
            };

            console.log(data.id);

            $.ajax({
                type: "post",
                url: "/bookReview/save",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(data),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                }
            }).done(function () {
                alert("독서록을 저장하였습니다.");
                window.location.href = "/";
            }).fail(function (error) {
                window.alert(JSON.stringify(error));
            });
        });
    }
};

findId.init();
signup.init();
bookSearch.init();
bookReview.saveReview();