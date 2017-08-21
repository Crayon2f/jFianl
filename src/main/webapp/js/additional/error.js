/**
 * Created by gou_feifan on 2017/8/15 11:56.
 */
(function () {

    $('button.go-back').on('click', function () {

        window.history.back();
    });

    $('button.go-home').on('click', function () {

        window.location.href = '/';
    });

})(window.jQuery);