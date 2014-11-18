function loginbutton() {
    $.ajax({
            type:"POST",
            url: "Login",
             data: {
                "user": $("#usr").val(),
                "pass": $("#pass").val(),
            },
        }).success(function(data){
            document.getElementById("result").innerHTML=data;
            alert(data);
            alert('success!!');
        }).error(function(data){
            alert('error!!!');
        });
}