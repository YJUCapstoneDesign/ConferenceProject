import { S3Client, ListObjectsV2Command, DeleteObjectCommand, PutObjectCommand,ListObjectsCommand,GetObjectCommand } from "@aws-sdk/client-s3";


export async function uploadToS3(crazyJson, teamId,topic) {
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

  const now = new Date();

  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1 필요
  const day = String(now.getDate()).padStart(2, '0');
  const hours = String(now.getHours()).padStart(2, '0');
  const minutes = String(now.getMinutes()).padStart(2, '0');
  const seconds = String(now.getSeconds()).padStart(2, '0');

  const formattedDate = `${year}${"년"}${month}${"월"}${day}${"일"}${hours}${"시"}${minutes}${"분"}${seconds}${"초"}`;


  try {
    // 새 파일 업로드
    const uploadParams = {
      Bucket: bucketName,
      Key: `${teamId}/crazy/${topic}/${formattedDate}crazy.txt`,
      Body: crazyJson,
      ACL: 'public-read',
    };
    const uploadCommand = new PutObjectCommand(uploadParams);
    await s3Client.send(uploadCommand);
    // S3 버킷에 있는 파일 목록 조회
    const listParams = {
      Bucket: bucketName,
      Prefix: `${teamId}/crazy/${topic}/`,
    };
    const listCommand = new ListObjectsCommand(listParams);
    const { Contents } = await s3Client.send(listCommand);
    // 파일이 5개 이상인 경우, 가장 오래된 파일 삭제
    if (Contents.length >= 6) {
      const oldestFileKey = Contents[0].Key; // 가장 오래된 파일
      const deleteParams = {
        Bucket: bucketName,
        Key: oldestFileKey,
      };
      const deleteCommand = new DeleteObjectCommand(deleteParams);
      await s3Client.send(deleteCommand);
    }

    return { success: true };
  } catch (error) {
    console.error('파일 업로드 실패:', error);
    return { success: false, error };
  }
}
