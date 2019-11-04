package no.unicornis.altinn.models.response;

import no.altinn.brokerserviceexternalec.BrokerServiceAvailableFile;
import no.unicornis.altinn.models.AltinnFile;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by taldev on 17/10/16.
 *
 * Helper class intended for JSON parsing. Simpler and more limited set of variables ensure that JSON output
 * from the web service is less cluttered.
 */
public class AvailableFilesResponse {

    @SuppressWarnings({"MismatchedQueryAndUpdateOfCollection", "FieldCanBeLocal"})
    private final ArrayList<AltinnFile> fileList;

    public ArrayList<AltinnFile> getFileList() {
        return this.fileList;
    }

    public AvailableFilesResponse(List<BrokerServiceAvailableFile> files) {
        this.fileList = new ArrayList<>();

        for (BrokerServiceAvailableFile f: files) {
            AltinnFile file = new AltinnFile();
            file.setFileName(f.getFileName().getValue());
            file.setFileReference(f.getFileReference());
            file.setFileSize(f.getFileSize());
            file.setFileStatus(f.getFileStatus().value());
            file.setReceiptID(f.getReceiptID());
            file.setReportee(f.getReportee().getValue());
            file.setSendersReference(f.getSendersReference().getValue());
            file.setSentDate(f.getSentDate());

            fileList.add(file);
        }
    }
}
