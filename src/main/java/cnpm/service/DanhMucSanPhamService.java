package cnpm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cnpm.dao.DanhMucSanPhamDAO;
import cnpm.entity.DanhMucSanPham;

@Service
public class DanhMucSanPhamService {
	
	@Autowired
	DanhMucSanPhamDAO danhMucSanPhamDAO;
	
	public DanhMucSanPham getByMaDM(Integer maDM) {
		return danhMucSanPhamDAO.getByMaDM(maDM);
	}
	
	public List<DanhMucSanPham> getDSDanhMuc(){
		return danhMucSanPhamDAO.getDSDanhMuc();
	}
	
	public Boolean themDM(DanhMucSanPham danhMucSP) {
		return danhMucSanPhamDAO.them(danhMucSP);
	}
	
	public Boolean suaDM(DanhMucSanPham danhMucSP) {
		return danhMucSanPhamDAO.sua(danhMucSP);
	}
	
	public Boolean xoaDM(DanhMucSanPham danhMucSP) {
		return danhMucSanPhamDAO.xoa(danhMucSP);
	}
}
