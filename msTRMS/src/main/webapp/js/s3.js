var albumBucketName = 'ms84103newbucket';
var bucketRegion = 'us-east-1';
var IdentityPoolId = 'us-east-1:c251ec74-f43c-43fb-b3ff-ce5c33754aff';

AWS.config.update({
  region: bucketRegion,
  credentials: new AWS.CognitoIdentityCredentials({
    IdentityPoolId: IdentityPoolId
  })
});

var s3 = new AWS.S3({
  apiVersion: '2006-03-01',
  params: {Bucket: albumBucketName}
});

function uploadFile(file, fileKey) {
    s3.upload({
      Key: fileKey,
      Body: file,
      ACL: 'public-read'
    }, function(err, data) {
      if (err) {
        console.log('There was an error uploading your file: %s', err.message);
      }
    });
  }