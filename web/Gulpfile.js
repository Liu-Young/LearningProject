var gulp = require('gulp');
var jshint = require('gulp-jshint');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');
var sass = require('gulp-sass');

// 语法检查
gulp.task('jshint', function () {
    return gulp.src('javascript/jquery-1.11.2.js')
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});

// 合并文件之后压缩代码
gulp.task('minify', function (){
    return gulp.src('src/*.js')
        .pipe(concat('all.js'))
        .pipe(gulp.dest('dist'))
        .pipe(uglify())
        .pipe(rename('all.min.js'))
        .pipe(gulp.dest('dist'));
});

// 监视文件的变化
gulp.task('watch', function () {
    gulp.watch('src/*.js', ['jshint', 'minify']);
});

// 复制到发布目录
gulp.task('cphtml', function(){
    return gulp.src(['*.html'
        ,'bower_components/html5shiv/dist/html5shiv.min.js'
        ,'bower_components/respond/dest/respond.min.js'
        ,'images/*'
        ,'javascript/jquery-1.11.2.min.js'
    ], {base: './'})
        .pipe(gulp.dest('build'));
});

// sass
gulp.task('sass', function(){
    return gulp.src(['sass/style.scss', 'sass/ie8.scss'])
        .pipe(sass())
        .pipe(gulp.dest('build/css'));
});

// 注册缺省任务
gulp.task('default', ['cphtml', 'sass']);