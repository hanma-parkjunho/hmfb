package hmfb.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**

 * @FileName : ErrorCode.java

 * @작성자 : 송원호

 * @작성일 : 2022. 12. 28 

 * @프로그램 설명 : ErrorCode 정의 클레스

 * @변경이력 :

 */

@AllArgsConstructor
@Getter
public enum ErrorCode {
	
	E101(500, "User Login Error", "error.user.SecurityErrMsg"),
	E102(500, "User Name Not Foun Error", "error.user.UsernameNotFoundErrMsg"),
	E103(500, "BadCredentials Error", "error.user.BadCredentialsErrMsg"),
	E104(500, "Auth Not Foun Error", "error.user.AuthDvcdNotFoundErrMsg"),
	E105(500, "AES256 Encrypt Error", "error.aes256.EncryptErrMsg"),
	E106(500, "AES256 Decrypt Error", "error.aes256.DecryptErrMsg"),
	
	E110(110, "UnsupportedEncodingException Error", "error.common.UnsupportedEncodingErrMsg"),
	E111(111, "IOException Error", "error.common.IOErrMsg"),
	E112(112, "Not Found Server", "error.common.NotFoundServerErrMsg"),
	E113(113, "Not Found [-DinstallPath] System property", "error.common.NotFoundInstallPathErrMsg"),
	E114(114, "Miss request parameter", "error.common.MissParamErrMsg"),
	E115(115, "Cannot shutdown AdminServer", "error.common.ShutdownErrMsg"),
	E116(116, "Invalid input value", "error.common.InvalidInputErrMsg"),
	E117(117, "Not found code", ""),  // 존재하지 않는 코드입니다.
	
	E401(401, "Unauthorized", "error.cmmn.UnauthorizedErrMsg"),
	E403(403, "Forbidden", "error.cmmn.ForbiddenErrMsg"),
	E404(404, "Not Found", "error.cmmn.NotFoundErrMsg"),
	E500(500, "Internal Server Error", "error.cmmn.InternalServerErrMsg"),
	
	/* BATCH 에러 메시지 : 801~899 */
	E801(801, "Occured System Error", ""), // 시스템 오류가 발생했습니다. 오류 상세 메시지를 확인해 주세요


	E809(809, "failed to create directory for batch data file", "error.batch.CreateDirErrMsg"),	// 배치 데이터 파일을 위한 입출력 디렉토리를 생성할 때 오류가 발생했습니다.
	E810(810, "It's not batch type with datasource for file", "error.batch.FileInErrMsg"),		// File 입력 유형의 배치 작업이 아닙니다.
	E811(811, "It's not batch type for output of file", "error.batch.FileOutErrMsg"),			// File 출력 유형의 배치 작업이 아닙니다.
	E812(812, "Not found batch id", "error.batch.NotFoundBatchIdErrMsg"),						// 배치 식별자가 존재하지 않습니다.
	E813(813, "Not found execution history", "error.batch.NotFoundExecutionErrMsg"),			// 해당 실행 내역을 찾을 수 없습니다.
	E814(814, "This batch is not running", "error.batch.NoRunningErrMsg"),						// 해당 배치는 실행 중이 아닙니다.
	E815(815, "This batch is running", "error.batch.RunningErrMsg"),							// 실행 중인 배치 작업입니다.
	E816(816, "This batch cannot restart", "error.batch.RestartErrMsg"),						// 배치를 재시작할 수 없습니다.
	E817(817, "This batch is already completed", "error.batch.AlreadyExecutionErrMsg"),			// 이미 실행 완료된 배치입니다.
	E818(818, "Input prarameter is invalid", "error.batch.InvalidParamErrMsg"),					// 입력 파라미터가 유효하지 않습니다.
	E819(819, "No defined I/O meta in source code of the batch program", "error.batch.NotDefineIOErrMsg"), // 해당 배치 프로그램에서 IO meta 를 정의하지 않았습니다.
	E820(820, "Not found file in the path", "error.batch.NotFoundFileErrMsg"),					// 해당 경로에 파일이 존재하지 않습니다.
	E821(821, "USE_YN flag of this batch is set to N", "error.batch.UserFlagErrMsg");			// 해당 배치의 사용여부가 사용안함으로 설정되어 있습니다.

	private int status;
	private String error;
	private final String message;
	
}
