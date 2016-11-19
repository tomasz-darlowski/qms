/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates

 */
package qmsjee.view.model;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import org.primefaces.model.SelectableDataModel;
import qmsjee.entities.entity.FileEntity;
import qmsjee.services.entityServices.interfaces.IFileService;

/**
 *
 * @author darlotom
 */
public class FileDataModel extends ListDataModel<FileEntity> implements SelectableDataModel<FileEntity>, Serializable {

    @Inject
    IFileService fileService;

    public FileDataModel() {
    }

    public FileDataModel(List<FileEntity> data) {
        super(data);
    }

    @Override
    public Object getRowKey(FileEntity object) {
        return ((Long) object.getId()).toString();
    }

    @Override
    public FileEntity getRowData(String rowKey) {
        return (FileEntity) fileService.findByID(Long.parseLong(rowKey));
    }

}
