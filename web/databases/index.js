var MongoClient = require('mongodb').MongoClient,
    test = require('assert');

// Connection url
var url = 'mongodb://120.24.228.163:27017/test';
// Connect using MongoClient

MongoClient.connect(url, function (err, db) {
    if (err) { throw err; }

    var post = { title: "Title one", content: "lorems fwfw ef ewf wef weg gjwgewfwefjewfwef", postData: new Date() }

    var postCollection = db.collection('posts');
    postCollection.insert(post, function (err, result) {
        if (err) throw err;



        db.close();
    });
});