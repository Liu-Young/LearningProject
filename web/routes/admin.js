var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function(req, res, next) {
  //console.info(req.route);
  res.render('admin/index');
});

module.exports = router;
