package abl.frd.qremit.converter.nafex.service;
import abl.frd.qremit.converter.nafex.helper.BeftnModelServiceHelper;
import abl.frd.qremit.converter.nafex.model.BeftnModel;
import abl.frd.qremit.converter.nafex.repository.BeftnModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class BeftnModelService {
    @Autowired
    BeftnModelRepository beftnModelRepository;
    public ByteArrayInputStream load(String fileId, String fileType) {
        List<BeftnModel> beftnModels = beftnModelRepository.findAllBeftnModelHavingFileInfoId(Long.parseLong(fileId));
        ByteArrayInputStream in = BeftnModelServiceHelper.BeftnModelsToExcel(beftnModels);
        return in;
    }
    public ByteArrayInputStream loadAll() {
        List<BeftnModel> beftnModels = beftnModelRepository.findAllBeftnModel();
        ByteArrayInputStream in = BeftnModelServiceHelper.BeftnModelsToExcel(beftnModels);
        return in;
    }

    public ByteArrayInputStream loadIncentive(String fileId, String fileType) {
        List<BeftnModel> beftnModels = beftnModelRepository.findAllBeftnModelHavingFileInfoIdForIncentive(Long.parseLong(fileId));
        ByteArrayInputStream in = BeftnModelServiceHelper.BeftnIncentiveModelsToExcel(beftnModels);
        return in;
    }
    public ByteArrayInputStream loadAllIncentive() {
        List<BeftnModel> beftnModels = beftnModelRepository.findAllBeftnModelForIncentive();
        ByteArrayInputStream in = BeftnModelServiceHelper.BeftnIncentiveModelsToExcel(beftnModels);
        return in;
    }
}
