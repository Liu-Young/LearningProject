var fs = require('fs');

exports.lowerCase = function (path) {
    var stat = fs.lstatSync(path);
    if (stat.isDirectory()) {
        fs.readdir(path, function (err, files) {
            if (err) return;

            for (var i = 0; i < files.length; i++) {
                exports.lowerCase(path + '\\' + files[i]);
            }
        });
    } else {
        var newPath = path.replace(/JPG/, "jpg");
        fs.rename(path, newPath, function () {
            console.log('' + path + '-->' + newPath);
        });
    }

}