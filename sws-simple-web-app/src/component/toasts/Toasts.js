import { Toast } from 'antd-mobile';

const DEFAULT_SHOWTIME = 1.5;

export function textOnlyToast(text, showTime=DEFAULT_SHOWTIME) {
  Toast.info(text, showTime);
}

/** 成功 */
export function successToast(text, showTime=DEFAULT_SHOWTIME) {
  Toast.success(text, showTime);
}
/** 失败 */
export function failToast(text, showTime=DEFAULT_SHOWTIME) {
  Toast.fail(text, showTime);
}

/** 离线 */
export function offline(text, showTime=DEFAULT_SHOWTIME) {
  Toast.offline(text, showTime);
}

export function loadingToast() {
  Toast.loading('Loading...', 1, () => {
    console.log('Load complete !!!');
  });
}
