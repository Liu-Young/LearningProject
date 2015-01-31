var connect = require('connect');

function logger(req, res, next) {
    console.log('%s %s', req.method, req.url);
    next(new Error('Unauthorized'));
}

function hello(req, res, next){
    res.setHeader('Content-Type', 'text/plain');
    res.end('hello world.')
}

connect().use(logger).use(hello).listen(300);