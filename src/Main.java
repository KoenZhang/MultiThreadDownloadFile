import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 下载链接
        String url = null;
        if (args == null || args.length == 0) {
            for (;;) {
                System.out.println("请输入下载链接: ");
                Scanner scanner = new Scanner(System.in);
                url = scanner.next();
                if (url != null) {
                    break;
                }
            }
        } else {
            url = args[0];
        }

        System.out.println(url);
    }
}
