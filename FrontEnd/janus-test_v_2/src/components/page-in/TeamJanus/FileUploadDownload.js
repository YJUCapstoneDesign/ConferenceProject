import {
  S3Client,
  ListObjectsV2Command,
  GetObjectCommand,
} from "@aws-sdk/client-s3";

export async function getTeamFilesText(teamId,topic) {
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
    // 1. 파일 목록 가져오기
    const listParams = {
      Bucket: bucketName,
      Prefix: `${teamId}/crazy/${topic}/`, // ${teamId} 폴더 안의 파일들만을 가져오도록 설정
    };

    const command = new ListObjectsV2Command(listParams);
    const listResponse = await s3Client.send(command);

    if (listResponse.Contents && listResponse.Contents.length > 0) {
      const fileKeys = listResponse.Contents.map((object) => object.Key);

      // 2. 각 파일의 텍스트를 배열에 저장
      const fileTexts = [];
      for (const fileKey of fileKeys) {
        // 2.1 파일 다운로드
        const downloadParams = {
          Bucket: bucketName,
          Key: fileKey,
        };

        const downloadCommand = new GetObjectCommand(downloadParams);
        const downloadResponse = await s3Client.send(downloadCommand);

        // 2.2 파일 내용 읽어오기
        const fileContent = await downloadResponse.Body.transformToString();
        fileTexts.push(fileContent); // 각 파일 텍스트를 배열에 추가
      }

      // 3. 각 파일의 텍스트가 담긴 배열 반환
      return fileTexts;
    } else {
      // No files found in the bucket
      console.warn(
        `No files found in the bucket: ${bucketName} for team ID: ${teamId}`
      );
      return []; // 반환 값으로 빈 배열을 반환
    }
  } catch (error) {
    console.error("파일 목록 및 내용 불러오기 실패:", error);
    throw error;
  }
}