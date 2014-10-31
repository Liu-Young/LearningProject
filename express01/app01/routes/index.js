var express = require('express');
var router = express.Router();
var photos = require('./photos');

/* GET home page. */
router.get('/', function(req, res) {
  res.render('index', { title: 'Express' });
});

router.get('/photos', function (req, res) {
    res.render('photos/index', {title: 'Express Photo',photos:[{name:"Node.js Logo"},{name:"Ryan Speaking"}]});
});

//router.get('/upload', photos.form);
//router.post('/upload', photos.submit('/public/uploads'));

module.exports = router;
