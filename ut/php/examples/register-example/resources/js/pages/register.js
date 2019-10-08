$(function() {
    $('#avatar').on('change', function(e) {
        if (this.files.length) {
            $('.js-avatar-file-label').text(this.files[0].name);
        }
    });
});
