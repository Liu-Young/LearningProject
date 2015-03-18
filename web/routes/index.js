var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index');
});
router.get('/article-bonded.html', function(req, res, next) {
  res.render('article-bonded');
});
router.get('/article-info.html', function(req, res, next) {
  res.render('article-info');
});
router.get('/article-logistics.html', function(req, res, next) {
  res.render('article-logistics');
});
router.get('/article-trade.html', function(req, res, next) {
  res.render('article-trade');
});

router.post('/sendMessage', function (req, res, next) {
    res.send('Hello~~');
});

module.exports = router;
