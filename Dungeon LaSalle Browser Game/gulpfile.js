var gulp = require('gulp');
var uglify = require('gulp-uglify');
var sass = require('gulp-sass');
var htmlmin = require('gulp-htmlmin');




gulp.task('html',function(){
    return gulp.src('src/*.html')
    .pipe(htmlmin({ collapseWhitespace: true }))
    .pipe(gulp.dest('build'));
});

gulp.task('sass',function(){
    gulp.src('src/css/*.scss')
    .pipe(sass().on('error', sass.logError))
    .pipe(gulp.dest('build/css'));
});

gulp.task('js',function(){
    gulp.src('src/js/*.js')
    .pipe(uglify())
    .pipe(gulp.dest('build/js'));
});

gulp.task('media',function(){
    gulp.src('src/media/images/**')
    .pipe(gulp.dest('build/media/images'));
});

gulp.task('img',function(){
    gulp.src('src/img/**')
    .pipe(gulp.dest('build/img'));
});

gulp.task('default', ['html','sass','js','media','img']);

gulp.task('watch',function() {
    gulp.watch('src/*.html',['html']);
    gulp.watch('src/css/*.scss',['sass']);
    gulp.watch('src/js/*.js',['js']);
    gulp.watch('src/media/images/**',['media']);
    gulp.watch('src/img/**',['img']);
});
