package android.print;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.microsftlogin.HomePage;

import java.io.File;

public class PdfPrint extends AppCompatActivity {
    Context context;
    private static final String TAG = PdfPrint.class.getSimpleName();
    private final PrintAttributes printAttributes;

    public PdfPrint(Context context, PrintAttributes printAttributes) {
        this.printAttributes = printAttributes;
        this.context = context;
    }

    public void print(final PrintDocumentAdapter printAdapter, final File path, final String fileName) {
        printAdapter.onLayout(null, printAttributes, null, new PrintDocumentAdapter.LayoutResultCallback() {
            @Override
            public void onLayoutFinished(PrintDocumentInfo info, boolean changed) {
                printAdapter.onWrite(new PageRange[]{PageRange.ALL_PAGES}, getOutputFile(path, fileName), new CancellationSignal(), new PrintDocumentAdapter.WriteResultCallback() {
                    @Override
                    public void onWriteFinished(PageRange[] pages) {
                        super.onWriteFinished(pages);
                    }
                });
            }
        }, null);
    }

    private ParcelFileDescriptor getOutputFile(File path, String fileName) {
        if (ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                   111);
        }

        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, fileName);
        try {
            file.createNewFile();
            return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE);
        } catch (Exception e) {
            Log.e(TAG, "Failed to open ParcelFileDescriptor", e);
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
