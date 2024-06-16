import { S3Client, ListObjectsV2Command, DeleteObjectCommand, PutObjectCommand,ListObjectsCommand,GetObjectCommand } from "@aws-sdk/client-s3";

export async function uploadToS3(image, username, filename) {
  // AWS S3 관련 설정
  const bucketName = process.env.REACT_APP_AWS_BUCKET_NAME;
  const region = process.env.REACT_APP_AWS_REGION;
  const accessKeyId = process.env.REACT_APP_AWS_ACCESS_KEY_ID;
  const secretAccessKey = process.env.REACT_APP_AWS_SECRET_ACCESS_KEY;

  const s3Client = new S3Client({
    region,
    credentials: {
      accessKeyId: accessKeyId,
      secretAccessKey: secretAccessKey,
    },
  });

  try {
    // S3 버킷에 있는 파일 목록 조회 (오류 처리 추가)
    const listParams = {
      Bucket: bucketName,
      Prefix: `${username}/`,
    };
    const listCommand = new ListObjectsCommand(listParams);
    const listResponse = await s3Client.send(listCommand);

    // 파일 목록이 존재하는 경우 가장 오래된 파일 삭제
    if (listResponse.Contents && listResponse.Contents.length >= 1) {
      const oldestFileKey = listResponse.Contents[0].Key; 
      const deleteParams = {
        Bucket: bucketName,
        Key: oldestFileKey,
      };
      const deleteCommand = new DeleteObjectCommand(deleteParams);
      await s3Client.send(deleteCommand);
    }

    // 새 파일 업로드
    const uploadParams = {
      Bucket: bucketName,
      Key: `${username}/${filename}`,
      Body: image,
      ACL: 'public-read',
    };
    const uploadCommand = new PutObjectCommand(uploadParams);
    await s3Client.send(uploadCommand);

    return { success: true };
  } catch (error) {
    console.error('파일 업로드 실패:', error);
    return { success: false, error };
  }
}

export async function listUploadedFiles(username) {
  // AWS S3 관련 설정
  const bucketName = process.env.REACT_APP_AWS_BUCKET_NAME;
  const region = process.env.REACT_APP_AWS_REGION;
  const accessKeyId = process.env.REACT_APP_AWS_ACCESS_KEY_ID;
  const secretAccessKey = process.env.REACT_APP_AWS_SECRET_ACCESS_KEY;

  const s3Client = new S3Client({
    region,
    credentials: {
      accessKeyId: accessKeyId,
      secretAccessKey: secretAccessKey,
    },
  });

  try {
    const listParams = {
      Bucket: bucketName,
      Prefix: `${username}/`, // ${teamId} 폴더 안의 파일들만을 가져오도록 설정
    };

    const command = new ListObjectsV2Command(listParams);
    const response = await s3Client.send(command);

    // 파일 URL 목록 생성 및 반환
    return response.Contents.map((object) => {
      const fileUrl = `https://${bucketName}.s3.${region}.amazonaws.com/${object.Key}`; // URL 생성
      return fileUrl;
    });
  } catch (error) {
    console.error('파일 목록 불러오기 실패:', error);
    return { success: false, error };
  }
}