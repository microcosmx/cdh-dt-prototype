/**
 * TODO
 */

$("#post-request").bind("click", function(){
    $.ajax({
        url: '/hello1', //http://10.141.212.21:12300/demo
        datatype: 'json',
        type: 'get',
        success: function (data) {
            alert(data);
        },
        error: function () {
            alert("Get - wrong");
        }
    });
});

