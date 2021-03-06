package cnpm.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cnpm.entity.*;

import cnpm.service.*;

@Controller
@RequestMapping("/quanly/")
public class QuanLyController {
	@Autowired
	NhanVienService nhanVienService;

	@Autowired
	TaiKhoanService taiKhoanService;

	@Autowired
	KhachHangService khachHangService;

	@Autowired
	DanhMucSanPhamService danhMucSanPhamService;

	@Autowired
	ChiTietSanPhamService chiTietSanPhamService;

	@Autowired
	ChiTietDonHangService chiTietDonHangService;

	@Autowired
	SizeService sizeService;

	@Autowired
	MauService mauService;

	@Autowired
	SanPhamService sanPhamService;

	@Autowired
	DonHangService donHangService;

	@Autowired
	UtilsService utilService;

//	============== Model - Attribute ==============
	@ModelAttribute("thongTinCaNhan")
	public NhanVien thongTinCaNhan() {
		return new NhanVien();
	}

	@ModelAttribute("danhSachDonHang")
	public List<DonHang> getDSDH() {
		return donHangService.getDSDonHang();
	}

	@ModelAttribute("danhSachMau")
	public List<MauSanPham> getDSMau() {
		List<MauSanPham> list = mauService.getDSMau();
		return list;
	}

	@ModelAttribute("danhSachSize")
	public List<SizeSanPham> getDSSize() {
		List<SizeSanPham> list = sizeService.getDSSize();
		return list;
	}

	@ModelAttribute("danhSachSanPham")
	public List<SanPham> getDSSanPham() {
		List<SanPham> list = sanPhamService.getDSSanPham();
		return list;
	}

	@ModelAttribute("danhSachNhanVien")
	public List<NhanVien> getDSNhanVien() {
		List<NhanVien> list = nhanVienService.getDSNhanVien();
		return list;
	}

	@ModelAttribute("danhSachKhachHang")
	public List<KhachHang> getDSKhachHang() {
		List<KhachHang> list = khachHangService.getDSKhachHang();
		return list;
	}

	@ModelAttribute("thongTinSP")
	public SanPham thongTinSP() {
		return new SanPham();
	}

	@ModelAttribute("sanPhamMoi")
	public SanPham sanPhamMoi() {
		return new SanPham();
	}
	
	@ModelAttribute("chiTietShanPhamMoi")
	public ChiTietSanPham chiTietSanPhamMoi() {
		return new ChiTietSanPham();
	}

	@ModelAttribute("thongTinDH")
	public DonHang thongTinDH() {
		return new DonHang();
	}

	@ModelAttribute("CTSP")
	public ChiTietSanPham ctSPMoi() {
		return new ChiTietSanPham();
	}

	@ModelAttribute("nhanVienMoi")
	public NhanVien thongTinNV() {
		NhanVien thongTinNV = new NhanVien();
		return thongTinNV;
	}

	@ModelAttribute("thongTinNV")
	public NhanVien thongtinNv(ModelMap model) {

		return new NhanVien();
	}

	@ModelAttribute("khachHangMoi")
	public KhachHang thongTinKH() {
		KhachHang thongTinKH = new KhachHang();
		return thongTinKH;
	}

	@ModelAttribute("thongTinKH")
	public KhachHang thongtinKh(ModelMap model) {

		return new KhachHang();
	}

	// danh muc

	@ModelAttribute("danhSachDanhMucSanPham")
	public List<DanhMucSanPham> dsDanhMucSanPham() {
		List<DanhMucSanPham> list = danhMucSanPhamService.getDSDanhMuc();
		return list;
	}

	@ModelAttribute("danhMucMoi")
	public DanhMucSanPham thongtinDM() {
		DanhMucSanPham thongTinDM = new DanhMucSanPham();
		return thongTinDM;
	}

	@ModelAttribute("thongTinDM")
	public DanhMucSanPham thongtinDm(ModelMap model) {

		return new DanhMucSanPham();
	}


	@RequestMapping(value = "tongquan", method = RequestMethod.GET)
	public String getViewTongQuan() {

		return "quantri/quanly/tongquan";
	}

	@RequestMapping(value = "nhanvien", method = RequestMethod.GET)
	public String getViewNhanVien(ModelMap model) {
		model.addAttribute("isOpenModalInfo", false);

		return "quantri/quanly/nhanvien";
	}

	@RequestMapping(value = "danhmucsp", method = RequestMethod.GET)
	public String getViewDanhMuc(ModelMap model) {
		model.addAttribute("isOpenModalInfo", false);

		return "quantri/quanly/danhmucsp";
	}

	@RequestMapping(value = "nhacungcap", method = RequestMethod.GET)
	public String getViewNhaCungCap(ModelMap model) {
		model.addAttribute("isOpenModalInfo", false);

		return "quantri/quanly/nhacungcap";
	}

	@RequestMapping(value = "nhanvien", params = "themNV", method = RequestMethod.POST)
	public String themMoiNhanVien(ModelMap model, @ModelAttribute("nhanVienMoi") NhanVien nhanvien,
			@RequestParam("anhMoi") MultipartFile anh, BindingResult errors) {
		if (nhanvien.getHo().trim().isEmpty()) {
			errors.rejectValue("ho", "nhanVienMoi", "H??? kh??ng ???????c ????? tr???ng");
		}

		if (nhanvien.getTen().trim().isEmpty()) {
			errors.rejectValue("ten", "nhanVienMoi", "T??n kh??ng ???????c ????? tr???ng");
		}

		if (nhanvien.getTaiKhoan().getEmail().trim().isEmpty() || !nhanvien.getTaiKhoan().getEmail().trim().matches(
				"^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
			errors.rejectValue("taiKhoan.email", "nhanVienMoi", "Email kh??ng h???p l??? ho???c b??? tr???ng");
		}

		if (nhanvien.getTaiKhoan().getMatKhau().trim().isEmpty()) {
			errors.rejectValue("taiKhoan.matKhau", "nhanVienMoi", "M???t kh???u kh??ng ???????c ????? tr???ng");
		}

		if (nhanvien.getPhai() != true && nhanvien.getPhai() != false) {
			errors.rejectValue("phai", "nhanVienMoi", "???");
		}

		if (nhanvien.getNgaySinh() == null) {
			errors.rejectValue("ngaySinh", "nhanVienMoi", "Ng??y sinh kh??ng ???????c ????? tr???ng");
		}

		if (nhanvien.getDiaChi().trim().isEmpty()) {
			errors.rejectValue("diaChi", "nhanVienMoi", "?????a ch??? kh??ng ???????c ????? tr???ng");
		}

		if (nhanvien.getCccd().trim().isEmpty()) {
			errors.rejectValue("cccd", "nhanVienMoi", "Cccd kh??ng ???????c ????? tr???ng");
		} else if (!nhanvien.getCccd().trim().matches("^[0-9]*$")) {
			errors.rejectValue("cccd", "nhanVienMoi", "Cccd kh??ng h???p l???");
		}

		if (nhanvien.getSdt().trim().isEmpty()) {
			errors.rejectValue("sdt", "nhanVienMoi", "S??? ??i???n tho???i kh??ng ???????c ????? tr???ng");
		} else if (!nhanvien.getSdt().trim().matches("^[0-9]*$")) {
			errors.rejectValue("sdt", "nhanVienMoi", "S??? ??i???n tho???i kh??ng h???p l???");
		}

		if (errors.hasErrors()) {
			model.addAttribute("isShowModalAddNew", true);
			return "quantri/quanly/nhanvien";
		}

		if (taiKhoanService.emailDaCo(nhanvien.getTaiKhoan().getEmail())) {
			errors.rejectValue("email", "nhanVienMoi", "Email ???? ???????c s??? d???ng");
			model.addAttribute("isShowModalAddNew", true);
			return "quantri/quanly/nhanvien";
		}

		if (nhanVienService.getBySdt(nhanvien.getSdt()) != null) {
			errors.rejectValue("sdt", "nhanVienMoi", "S??? ??i???n tho???i ???? ???????c s??? d???ng");
			model.addAttribute("isShowModalAddNew", true);
			return "quantri/quanly/nhanvien";
		}

		String hinh = "";

		if (!anh.isEmpty()) {
			hinh = utilService.luuFile(anh);
			if (!hinh.isEmpty()) {
				nhanvien.setAnh(hinh);
			}
		}

		TaiKhoan taiKhoan = taiKhoanService.setTK(nhanvien.getTaiKhoan().getEmail(),
				nhanvien.getTaiKhoan().getMatKhau());
		taiKhoanService.themNV(taiKhoan);

		nhanvien.setMaNV(nhanVienService.taoMaNVMoi());
		nhanvien.setTaiKhoan(taiKhoan);

		if (nhanVienService.themNV(nhanvien)) {
//			model.addAttribute("thongTinNV", new ThongTinCaNhan());
			model.addAttribute("nhanVienMoi", new NhanVien());
			model.addAttribute("isSuccess", true);
			model.addAttribute("alertMessage", "Th??m t??i kho???n th??nh c??ng");
			model.addAttribute("hinh", "");
			model.addAttribute("danhSachNhanVien", nhanVienService.getDSNhanVien());
		} else {
			taiKhoanService.xoaTK(taiKhoan);
			model.addAttribute("isSuccess", false);
			model.addAttribute("alertMessage", "Th??m t??i kho???n th???t b???i");
		}

		return "quantri/quanly/nhanvien";
	}

	@RequestMapping(value = "nhanvien/{maNV}", params = "thongtin", method = RequestMethod.GET)
	public String getThongtin1NhanVien(ModelMap model, @PathVariable("maNV") String maNV) {

		NhanVien nhanvien = nhanVienService.getByMaNV(maNV);
		if (nhanvien != null) {
			model.addAttribute("thongTinNV", nhanvien);
			model.addAttribute("isOpenModalInfo", true);

		}

		return "quantri/quanly/nhanvien";
	}

	@RequestMapping(value = "nhanvien/{maNV}", params = "suaThongtin", method = RequestMethod.GET)
	public String getSuaNhanVien(ModelMap model, @PathVariable("maNV") String maNV) {

		NhanVien nhanvien = nhanVienService.getByMaNV(maNV);

		if (nhanvien != null) {
			model.addAttribute("thongTinNV", nhanvien);
			model.addAttribute("isOpenModalEdit", true);

		}

		return "quantri/quanly/nhanvien";
	}

	@RequestMapping(value = "nhanvien/{maNV}", params = "suaNV", method = RequestMethod.POST)
	public String postSuaNhanVien(ModelMap model, @ModelAttribute("thongTinNV") NhanVien nhanvien,
			@RequestParam("anhMoi") MultipartFile anh, @PathVariable("maNV") String maNV, BindingResult errors) {

		if (nhanvien.getHo().trim().isEmpty()) {
			errors.rejectValue("ho", "thongTinNV", "H??? kh??ng ???????c ????? tr???ng");
		}

		if (nhanvien.getTen().trim().isEmpty()) {
			errors.rejectValue("ten", "thongTinNV", "T??n kh??ng ???????c ????? tr???ng");
		}

		if (nhanvien.getTaiKhoan().getEmail().trim().isEmpty() || !nhanvien.getTaiKhoan().getEmail().trim().matches(
				"^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
			errors.rejectValue("taiKhoan.email", "thongTinNV", "Email kh??ng h???p l??? ho???c b??? tr???ng");
		}

		if (nhanvien.getPhai() != true && nhanvien.getPhai() != false) {
			errors.rejectValue("phai", "thongTinNV", "???");
		}

		if (nhanvien.getNgaySinh() == null) {
			errors.rejectValue("ngaySinh", "thongTinNV", "Ng??y sinh kh??ng ???????c ????? tr???ng");
		}

		if (nhanvien.getCccd().trim().isEmpty()) {
			errors.rejectValue("cccd", "thongTinNV", "Cccd kh??ng ???????c ????? tr???ng");
		} else if (!nhanvien.getCccd().trim().matches("^[0-9]*$")) {
			errors.rejectValue("cccd", "thongTinNV", "Cccd kh??ng h???p l???");
		}

		if (nhanvien.getSdt().trim().isEmpty()) {
			errors.rejectValue("sdt", "thongTinNV", "S??? ??i???n tho???i kh??ng ???????c ????? tr???ng");
		} else if (!nhanvien.getSdt().trim().matches("^[0-9]*$")) {
			errors.rejectValue("sdt", "thongTinNV", "S??? ??i???n tho???i kh??ng h???p l???");
		}

		if (errors.hasErrors()) {
			model.addAttribute("isOpenModalEdit", true);
			return "quantri/quanly/nhanvien";
		}

		NhanVien nhanviencu = nhanVienService.getByMaNV(maNV);
		if (nhanviencu != null) {
			if (!nhanviencu.getHo().equals(nhanvien.getHo()))
				nhanviencu.setHo(nhanvien.getHo());
			if (!nhanviencu.getTen().equals(nhanvien.getTen())) {
				nhanviencu.setTen(nhanvien.getTen());
			}
			if (!nhanviencu.getNgaySinh().equals(nhanvien.getNgaySinh())) {
				nhanviencu.setNgaySinh(nhanvien.getNgaySinh());
			}
			if (!nhanviencu.getCccd().equals(nhanvien.getCccd())) {
				nhanviencu.setCccd(nhanvien.getCccd());
			}
			if (!nhanviencu.getDiaChi().equals(nhanvien.getDiaChi())) {
				nhanviencu.setDiaChi(nhanvien.getDiaChi());
			}

			if (!nhanviencu.getSdt().equals(nhanvien.getSdt())) {
				if (nhanVienService.getBySdt(nhanvien.getSdt()) != null) {
					errors.rejectValue("sdt", "thongTinNV", "S??? ??i???n tho???i ???? ???????c s??? d???ng");
					model.addAttribute("isOpenModalEdit", true);
					return "quantri/quanly/nhanvien";
				} else {
					nhanviencu.setSdt(nhanvien.getSdt());
				}
			}

			if (nhanviencu.getPhai() != nhanvien.getPhai()) {
				nhanviencu.setPhai(nhanvien.getPhai());
			}
			if (!nhanviencu.getTaiKhoan().getEmail().equals(nhanvien.getTaiKhoan().getEmail())) {
				errors.rejectValue("taiKhoan.email", "thongTinNV", "Email kh??ng ???????c thay ?????i");
				model.addAttribute("isOpenModalEdit", true);
				return "quantri/quanly/nhanvien";
			}

			if (nhanviencu.getTaiKhoan().getTrangThai() != nhanvien.getTaiKhoan().getTrangThai()) {
				nhanviencu.getTaiKhoan().setTrangThai(nhanvien.getTaiKhoan().getTrangThai());
			}

			if (!anh.isEmpty()) {
				String hinh = "";
				hinh = utilService.luuFile(anh);
				if (!hinh.isEmpty()) {
					nhanviencu.setAnh(hinh);
				}
			}

			if (nhanVienService.suaNV(nhanviencu)) {
				model.addAttribute("thongTinNV", new NhanVien());
				model.addAttribute("isSuccess", true);
				model.addAttribute("alertMessage", "S???a nh??n vi??n th??nh c??ng");
				model.addAttribute("hinh", "");
				model.addAttribute("danhSachNhanVien", nhanVienService.getDSNhanVien());
			}
		}

		else {
			model.addAttribute("isSuccess", false);
			model.addAttribute("alertMessage", "S???a nh??n vi??n th???t b???i");
		}

		return "quantri/quanly/nhanvien";
	}

	@RequestMapping(value = "nhanvien/{maNV}", params = "resetmatkhau", method = RequestMethod.POST)
	public String resetMatKhau(ModelMap model, @PathVariable("maNV") String maNV) {
		if (maNV.trim().isEmpty()) {
			model.addAttribute("isSuccess", false);
			model.addAttribute("alertMessage", "Reset m???t kh???u th???t b???i");
		} else {
			NhanVien nhanvien = nhanVienService.getByMaNV(maNV);
			if (nhanvien != null) {
				taiKhoanService.resetMK(nhanvien.getTaiKhoan());
				model.addAttribute("isSuccess", true);
				model.addAttribute("alertMessage", "Reset m???t kh???u th??nh c??ng");
			} else {
				model.addAttribute("isSuccess", false);
				model.addAttribute("alertMessage", "Reset m???t kh???u th???t b???i");
			}
		}

		return "quantri/quanly/nhanvien";
	}

	@RequestMapping(value = "khachhang", method = RequestMethod.GET)
	public String getViewKhachHang(ModelMap model) {
		model.addAttribute("isOpenModalInfo", false);

		return "quantri/quanly/khachhang";
	}

	/// Kh??ch h??ng

	@RequestMapping(value = "khachhang", params = "themKH", method = RequestMethod.POST)
	public String themMoiNhanVien(ModelMap model, @ModelAttribute("khachHangMoi") KhachHang khachhang,
			@RequestParam("anhMoi") MultipartFile anh, BindingResult errors) {
		if (khachhang.getHo().trim().isEmpty()) {
			errors.rejectValue("ho", "khachHangMoi", "H??? kh??ng ???????c ????? tr???ng");
		}

		if (khachhang.getTen().trim().isEmpty()) {
			errors.rejectValue("ten", "khachHangMoi", "T??n kh??ng ???????c ????? tr???ng");
		}

		if (khachhang.getTaiKhoan().getEmail().trim().isEmpty() || !khachhang.getTaiKhoan().getEmail().trim().matches(
				"^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
			errors.rejectValue("taiKhoan.email", "khachHangMoi", "Email kh??ng h???p l??? ho???c b??? tr???ng");
		}

		if (khachhang.getTaiKhoan().getMatKhau().trim().isEmpty()) {
			errors.rejectValue("taiKhoan.matKhau", "khachHangMoi", "M???t kh???u kh??ng ???????c ????? tr???ng");
		}

		if (khachhang.getPhai() != true && khachhang.getPhai() != false) {
			errors.rejectValue("phai", "khachHangMoi", "???");
		}

		if (khachhang.getNgaySinh() == null) {
			errors.rejectValue("ngaySinh", "khachHangMoi", "Ng??y sinh kh??ng ???????c ????? tr???ng");
		}

		if (khachhang.getDiaChi().trim().isEmpty()) {
			errors.rejectValue("diaChi", "khachHangMoi", "?????a ch??? kh??ng ???????c ????? tr???ng");
		}

		if (khachhang.getSdt().trim().isEmpty()) {
			errors.rejectValue("sdt", "khachHangMoi", "S??? ??i???n tho???i kh??ng ???????c ????? tr???ng");
		} else if (!khachhang.getSdt().trim().matches("^[0-9]*$")) {
			errors.rejectValue("sdt", "khachHangMoi", "S??? ??i???n tho???i kh??ng h???p l???");
		}

		if (errors.hasErrors()) {
			model.addAttribute("isShowModalAddNew", true);
			return "quantri/quanly/khachhang";
		}

		if (taiKhoanService.emailDaCo(khachhang.getTaiKhoan().getEmail())) {
			errors.rejectValue("email", "khachHangMoi", "Email ???? ???????c s??? d???ng");
			model.addAttribute("isShowModalAddNew", true);
			return "quantri/quanly/khachhang";
		}

		if (nhanVienService.getBySdt(khachhang.getSdt()) != null) {
			errors.rejectValue("sdt", "khachHangMoi", "S??? ??i???n tho???i ???? ???????c s??? d???ng");
			model.addAttribute("isShowModalAddNew", true);
			return "quantri/quanly/khachhang";
		}

		String hinh = "";
		System.out.println("anh " + anh);
		if (!anh.isEmpty()) {
			hinh = utilService.luuFile(anh);
			if (!hinh.isEmpty()) {
				khachhang.setAnh(hinh);
			}
		}

		TaiKhoan taiKhoan = taiKhoanService.setTK(khachhang.getTaiKhoan().getEmail(),
				khachhang.getTaiKhoan().getMatKhau());
		taiKhoanService.themKH(taiKhoan);

		khachhang.setMaKH(khachHangService.taoMaKHMoi());
		khachhang.setTaiKhoan(taiKhoan);

		if (khachHangService.themKH(khachhang)) {
//			model.addAttribute("thongTinNV", new ThongTinCaNhan());
			model.addAttribute("khachHangMoi", new KhachHang());
			model.addAttribute("isSuccess", true);
			model.addAttribute("alertMessage", "Th??m t??i kho???n th??nh c??ng");
			model.addAttribute("hinh", "");
			model.addAttribute("danhSachKhachHang", khachHangService.getDSKhachHang());
		} else {
			taiKhoanService.xoaTK(taiKhoan);
			model.addAttribute("isSuccess", false);
			model.addAttribute("alertMessage", "Th??m t??i kho???n th???t b???i");
		}

		return "quantri/quanly/khachhang";
	}

	@RequestMapping(value = "khachhang/{maKH}", params = "thongtin", method = RequestMethod.GET)
	public String getThongtin1KhachHang(ModelMap model, @PathVariable("maKH") String maKH) {

		KhachHang khachhang = khachHangService.getByMaKH(maKH);
		if (khachhang != null) {
			model.addAttribute("thongTinKH", khachhang);
			model.addAttribute("isOpenModalInfo", true);

		}

		return "quantri/quanly/khachhang";
	}

	@RequestMapping(value = "khachhang/{maKH}", params = "suaThongtin", method = RequestMethod.GET)
	public String getSuaKhachHang(ModelMap model, @PathVariable("maKH") String maKH) {

		KhachHang khachhang = khachHangService.getByMaKH(maKH);

		if (khachhang != null) {
			model.addAttribute("thongTinKH", khachhang);
			model.addAttribute("isOpenModalEdit", true);

		}

		return "quantri/quanly/khachhang";
	}

	@RequestMapping(value = "khachhang/{maKH}", params = "suaKH", method = RequestMethod.POST)
	public String postSuaKhachHang(ModelMap model, @ModelAttribute("thongTinKH") KhachHang khachhang,
			@RequestParam("anhMoi") MultipartFile anh, @PathVariable("maKH") String maKH, BindingResult errors) {

		if (khachhang.getHo().trim().isEmpty()) {
			errors.rejectValue("ho", "thongTinNV", "H??? kh??ng ???????c ????? tr???ng");
		}

		if (khachhang.getTen().trim().isEmpty()) {
			errors.rejectValue("ten", "thongTinNV", "T??n kh??ng ???????c ????? tr???ng");
		}

		if (khachhang.getTaiKhoan().getEmail().trim().isEmpty() || !khachhang.getTaiKhoan().getEmail().trim().matches(
				"^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")) {
			errors.rejectValue("taiKhoan.email", "thongTinNV", "Email kh??ng h???p l??? ho???c b??? tr???ng");
		}

		if (khachhang.getPhai() != true && khachhang.getPhai() != false) {
			errors.rejectValue("phai", "thongTinNV", "???");
		}

		if (khachhang.getNgaySinh() == null) {
			errors.rejectValue("ngaySinh", "thongTinNV", "Ng??y sinh kh??ng ???????c ????? tr???ng");
		}

		if (khachhang.getSdt().trim().isEmpty()) {
			errors.rejectValue("sdt", "thongTinNV", "S??? ??i???n tho???i kh??ng ???????c ????? tr???ng");
		} else if (!khachhang.getSdt().trim().matches("^[0-9]*$")) {
			errors.rejectValue("sdt", "thongTinNV", "S??? ??i???n tho???i kh??ng h???p l???");
		}
		

		if (errors.hasErrors()) {
			model.addAttribute("isOpenModalEdit", true);
			return "quantri/quanly/nhanvien";
		}

		KhachHang khachhangcu = khachHangService.getByMaKH(maKH);
		
		if (!khachhangcu.getTaiKhoan().getEmail().equals(khachhang.getTaiKhoan().getEmail())) {
			errors.rejectValue("taiKhoan.email", "thongTinNV", "Email kh??ng ???????c thay ?????i");
			model.addAttribute("isOpenModalEdit", true);
			return "quantri/quanly/nhanvien";
		}
		
		if (khachhangcu != null) {
			if (!khachhangcu.getHo().equals(khachhang.getHo()))
				khachhangcu.setHo(khachhang.getHo());
			if (!khachhangcu.getTen().equals(khachhang.getTen())) {
				khachhangcu.setTen(khachhang.getTen());
			}
			if (!khachhangcu.getNgaySinh().equals(khachhang.getNgaySinh())) {
				khachhangcu.setNgaySinh(khachhang.getNgaySinh());
			}
			if (!khachhangcu.getDiaChi().equals(khachhang.getDiaChi())) {
				khachhangcu.setDiaChi(khachhang.getDiaChi());
			}

			if (!khachhangcu.getSdt().equals(khachhang.getSdt())) {
				if (nhanVienService.getBySdt(khachhang.getSdt()) != null) {
					errors.rejectValue("sdt", "thongTinNV", "S??? ??i???n tho???i ???? ???????c s??? d???ng");
					model.addAttribute("isOpenModalEdit", true);
					return "quantri/quanly/nhanvien";
				} else {
					khachhangcu.setSdt(khachhang.getSdt());
				}
			}

			if (khachhangcu.getPhai() != khachhang.getPhai()) {
				khachhangcu.setPhai(khachhang.getPhai());
			}
			

			System.out.println(khachhangcu.getTaiKhoan().getTrangThai());
			System.out.println(khachhang.getTaiKhoan().getTrangThai());
			System.out.println(1);

			if (khachhangcu.getTaiKhoan().getTrangThai() != khachhang.getTaiKhoan().getTrangThai()) {
				khachhangcu.getTaiKhoan().setTrangThai(khachhang.getTaiKhoan().getTrangThai());
				System.out.println(2);
				System.out.println(khachhangcu.getTaiKhoan().getTrangThai());
			}

			if (!anh.isEmpty()) {
				String hinh = "";
				hinh = utilService.luuFile(anh);
				if (!hinh.isEmpty()) {
					khachhangcu.setAnh(hinh);
				}
			}

			if (khachHangService.suaKH(khachhangcu)) {
				model.addAttribute("thongTinKH", new KhachHang());
				model.addAttribute("isSuccess", true);
				model.addAttribute("alertMessage", "S???a kh??ch h??ng th??nh c??ng");
				model.addAttribute("hinh", "");
				model.addAttribute("danhSachKhachHang", khachHangService.getDSKhachHang());
			}
		}

		else {
			model.addAttribute("isSuccess", false);
			model.addAttribute("alertMessage", "S???a khachhang th???t b???i");
		}

		return "quantri/quanly/khachhang";
	}

	/// Danh m???c

	@RequestMapping(value = "danhmucsp", params = "themDM", method = RequestMethod.POST)
	public String themMoiDanhMuc(ModelMap model, @ModelAttribute("danhMucMoi") DanhMucSanPham danhmucsp,
			BindingResult errors) {
		if (danhmucsp.getTenDM().trim().isEmpty()) {
			errors.rejectValue("tenDM", "danhMucMoi", "T??n danh m???c kh??ng ???????c tr???ng");
		}

		if (errors.hasErrors()) {
			model.addAttribute("isShowModalAddNew", true);
			return "quantri/quanly/danhmucsp";
		}

		if (danhMucSanPhamService.themDM(danhmucsp)) {

			model.addAttribute("danhmucMoi", new DanhMucSanPham());
			model.addAttribute("isSuccess", true);
			model.addAttribute("alertMessage", "Th??m danh m???c th??nh c??ng");
			model.addAttribute("danhSachDanhMucSanPham", danhMucSanPhamService.getDSDanhMuc());
		} else {
			model.addAttribute("alertMessage", "Th??m danh m???c th???t b???i");
		}

		return "quantri/quanly/danhmucsp";
	}

	// S???a th??ng tin Danh m???c

	@RequestMapping(value = "danhmucsp/{maDM}", params = "thongtin", method = RequestMethod.GET)
	public String getThongtin1DanhMuc(ModelMap model, @PathVariable("maDM") Integer maDM) {

		DanhMucSanPham danhmuc = danhMucSanPhamService.getByMaDM(maDM);
		if (danhmuc != null) {
			model.addAttribute("thongTinDM", danhmuc);
			model.addAttribute("isOpenModalInfo", true);

		}

		return "quantri/quanly/danhmucsp";
	}

	@RequestMapping(value = "danhmucsp/{maDM}", params = "suaThongtin", method = RequestMethod.GET)
	public String getSuaDanhMuc(ModelMap model, @PathVariable("maDM") Integer maDM) {

		DanhMucSanPham danhMucsp = danhMucSanPhamService.getByMaDM(maDM);

		if (danhMucsp != null) {
			model.addAttribute("thongTinDM", danhMucsp);
			model.addAttribute("isOpenModalEdit", true);

		}

		return "quantri/quanly/danhmucsp";
	}

	@RequestMapping(value = "danhmucsp/{maDM}", params = "suaThongtin", method = RequestMethod.POST)
	public String postSuaDanhMuc(ModelMap model, @ModelAttribute("thongTinDM") DanhMucSanPham danhmucsp,
			@PathVariable("maDM") Integer maDM, BindingResult errors) {

		if (danhmucsp.getTenDM().trim().isEmpty()) {
			errors.rejectValue("tenDM", "thongTinDM", "T??n danh m???c kh??ng ???????c tr???ng");
		}
		if (errors.hasErrors()) {
			model.addAttribute("isShowModalAddNew", true);
			return "quantri/quanly/danhmucsp";
		}

		DanhMucSanPham danhmuccu = danhMucSanPhamService.getByMaDM(maDM);
		if (danhmuccu != null) {
			System.out.println(1);
			if (!danhmuccu.getTenDM().equals(danhmucsp.getTenDM()))
				danhmuccu.setTenDM(danhmucsp.getTenDM());
		}
		if (danhMucSanPhamService.suaDM(danhmuccu)) {
			model.addAttribute("thongTinDM", new DanhMucSanPham());
			model.addAttribute("isSuccess", true);
			model.addAttribute("alertMessage", "S???a danh m???c th??nh c??ng");
			model.addAttribute("danhSachDanhMucSanPham", danhMucSanPhamService.getDSDanhMuc());
		}

		else {
			model.addAttribute("isSuccess", false);
			model.addAttribute("alertMessage", "S???a danh m???c th???t b???i");
			System.out.println(3);
		}

		return "quantri/quanly/danhmucsp";
	}

	@RequestMapping(value = "danhmucsp", params = "xoadm", method = RequestMethod.POST)
	public String postXoaDanhMuc(ModelMap model, @RequestParam("maDM") Integer maDM) {

		// System.out.println(danhmucsp.getMaDM());
		DanhMucSanPham danhmucsp = danhMucSanPhamService.getByMaDM(maDM);
		if (danhmucsp == null) {
			if (danhmucsp.getSanPhams() != null) {
				model.addAttribute("isSuccess", false);
				model.addAttribute("alertMessage", "X??a danh m???c th???t b???i");
			}
		}
		if (danhmucsp.getSanPhams().size() == 0) {
			danhMucSanPhamService.xoaDM(danhmucsp);
			model.addAttribute("isSuccess", true);
			model.addAttribute("alertMessage", "X??a danh m???c th??nh c??ng");
			model.addAttribute("danhSachDanhMucSanPham", danhMucSanPhamService.getDSDanhMuc());

		}
		if (danhmucsp.getSanPhams().size() > 0) {
			model.addAttribute("isSuccess", false);
			model.addAttribute("alertMessage", "X??a danh m???c th???t b???i");
		}

		return "quantri/quanly/danhmucsp";
	}
	
	
	// ????n h??ng
	@RequestMapping(value = "donhang", method = RequestMethod.GET)
	public String getViewDonHang() {
		return "quantri/quanly/donhang";
	}

	@RequestMapping(value = "donhang/{maDH}", params = "thongtin", method = RequestMethod.GET)
	public String getThongTinDonHang(ModelMap model, @PathVariable("maDH") Integer maDH) {

		if (maDH != null) {
			DonHang donhang = donHangService.getByMaDH(maDH);

			if (donhang != null) {
				List<ChiTietDonHang> chitietdonhang = chiTietDonHangService.getDSByMaDH(maDH);
				if (chitietdonhang.size() > 0) {
					model.addAttribute("chitietdonhang", chitietdonhang);
				} else {
					model.addAttribute("chitietdonhang", new ArrayList<ChiTietDonHang>());
				}

				model.addAttribute("thongTinDH", donhang);
				model.addAttribute("isOpenModalInfo", true);
			}
		}

		return "quantri/quanly/donhang";
	}

	@RequestMapping(value = "donhang/{maDH}", params = "suaTrangthai", method = RequestMethod.GET)
	public String getTrangThaiNhanVien(ModelMap model, @PathVariable("maDH") Integer maDH) {

		DonHang donhang = donHangService.getByMaDH(maDH);

		System.out.println(donhang.getTrangThaiDH().getMaTTDH());

		if (donhang != null) {
			model.addAttribute("thongTinDH", donhang);
			model.addAttribute("isOpenModalEdit", true);

			System.out.println(2);
		}
		return "quantri/quanly/donhang";
	}

	@RequestMapping(value = "donhang/{maDH}", params = "suaDH", method = RequestMethod.POST)
	public String postTrangThaiDonHang(ModelMap model, @PathVariable("maDH") Integer maDH,
			@ModelAttribute("thongTinDH") DonHang donhang, BindingResult errors) {

		DonHang donhangcu = donHangService.getByMaDH(maDH);
		if (donhangcu != null) {
			if (donhangcu.getTrangThaiDH().getMaTTDH() == 3 && donhang.getTrangThaiDH().getMaTTDH() != 3) {

				errors.rejectValue("trangThaiDH", "thongTinDH", "????n h??ng ???? b??? h???y tr????c ????");
				model.addAttribute("isOpenModalEdit", true);
				return "quantri/quanly/donhang";
			}
			if (donhangcu.getTrangThaiDH().getMaTTDH() == 1 && donhang.getTrangThaiDH().getMaTTDH() == 4) {
				errors.rejectValue("trangThaiDH", "thongTinDH", "????n h??ng ch??a ???????c x??c nh???n");
				model.addAttribute("isOpenModalEdit", true);
				return "quantri/quanly/donhang";
			}

			if (donhangcu.getTrangThaiDH().getMaTTDH() == 2 && donhang.getTrangThaiDH().getMaTTDH() != 4) {
				errors.rejectValue("trangThaiDH", "thongTinDH", "????n h??ng kh??ng th??? h???y ho???c chuy???n th??nh ????n m???i");
				model.addAttribute("isOpenModalEdit", true);
				return "quantri/quanly/donhang";
			}

			if (donhangcu.getTrangThaiDH().getMaTTDH() == 4 && donhang.getTrangThaiDH().getMaTTDH() != 4) {
				errors.rejectValue("trangThaiDH", "thongTinDH", "????n h??ng ???? giao th??nh c??ng tr?????c ????");
				model.addAttribute("isOpenModalEdit", true);
				return "quantri/quanly/donhang";
			}

//			if (donhangcu.getTrangThaiDH().getMaTTDH() == 1 && donhang.getTrangThaiDH().getMaTTDH() == 2) {
//				List<ChiTietDonHang> ctdh = chiTietDonHangService.getDSByMaDH(maDH);
//				for(int i=0; i<ctdh.size(); i++) {
//					
//				}
//			}
			
			donhangcu.getTrangThaiDH().setMaTTDH(donhang.getTrangThaiDH().getMaTTDH());

			if (donHangService.suaDH(donhangcu)) {
				model.addAttribute("isSuccess", true);
				model.addAttribute("alertMessage", "Thay ?????i tr???ng th??i th??nh c??ng");
				model.addAttribute("danhSachDonHang", donHangService.getDSDonHang());
			} else {
				model.addAttribute("isSuccess", false);
				model.addAttribute("alertMessage", "Thay ?????i tr???ng th??a th???t b???i");
			}

		} else {
			model.addAttribute("isSuccess", false);
			model.addAttribute("alertMessage", "Thay ?????i tr???ng th??a th???t b???i");
		}

		return "quantri/quanly/donhang";
	}

	// san pham
	@RequestMapping(value = "sanpham", method = RequestMethod.GET)
	public String getViewSanpham() {
		return "quantri/quanly/sanpham";
	}

	@RequestMapping(value = "sanpham/{maSP}", params = "thongtin", method = RequestMethod.GET)
	public String thongTin1SanPham(ModelMap model, @PathVariable("maSP") Integer maSP) {
		SanPham sanpham = sanPhamService.getByMaSP(maSP);
		if (sanpham != null) {
			model.addAttribute("thongTinSP", sanpham);
			model.addAttribute("isOpenModalInfo", true);
		}
		return "quantri/quanly/sanpham";
	}

//	@RequestParam("anhMoi") MultipartFile anh,
	@RequestMapping(value = "sanpham", params = "themSP", method = RequestMethod.POST)
	public String themMoiSanPham(ModelMap model, @ModelAttribute("sanPhamMoi") SanPham sanpham,
			@RequestParam("anhMoi") MultipartFile anh,
			@RequestParam(name = "size", required = false) List<Integer> listsize, BindingResult errors) {

		if (sanpham.getTenSP().trim().isEmpty()) {
			errors.rejectValue("tenSP", "sanPhamMoi", "T??n s???n ph???m kh??ng ???????c ????? tr???ng");
		}

		if (sanpham.getGia() == 0) {
			errors.rejectValue("gia", "sanPhamMoi", "Gi?? kh??ng ???????c ????? tr???ng");
		}

		if (listsize == null) {
			model.addAttribute("size", "Ph???i ch???n ??t nh???t 1 size s???n ph???m");
			model.addAttribute("isShowModalAddNew", true);
			return "quantri/quanly/sanpham";
		}

		if (errors.hasErrors()) {
			model.addAttribute("isShowModalAddNew", true);
			return "quantri/quanly/sanpham";
		}

		if (anh == null) {
			model.addAttribute("anhMoi", "H??nh ???nh kh??ng th??? ????? tr???ng");
			return "quantri/quanly/sanpham";
		}

		String hinh = "";
		System.out.println("anh " + anh);
		if (!anh.isEmpty()) {
			hinh = utilService.luuFile(anh);
			if (!hinh.isEmpty()) {
				sanpham.setHinhAnh(hinh);
			}
		}

		if (sanPhamService.themSP(sanpham)) {

			int masp = sanPhamService.maSPCuoiCung();
			ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
			System.out.println(1);

			for (int i = 0; i < listsize.size(); i++) {
				System.out.println(2);
				SizeSanPham sizesanpham = sizeService.getByMaSize(listsize.get(i));
				chiTietSanPham.setSanPham(sanPhamService.getByMaSP(masp));
				chiTietSanPham.setSizeSanPham(sizesanpham);
				chiTietSanPham.setSoLuong(0);

				chiTietSanPhamService.themCTSP(chiTietSanPham);
				System.out.println(3);
			}
			model.addAttribute("sanPhamMoi", new SanPham());
			model.addAttribute("isSuccess", true);
			model.addAttribute("alertMessage", "Th??m s???n ph???m th??nh c??ng");
			model.addAttribute("hinh", "");
			model.addAttribute("danhSachSanPham", sanPhamService.getDSSanPham());
		} else {

			model.addAttribute("isSuccess", false);
			model.addAttribute("alertMessage", "Th??m t??i kho???n th???t b???i");
		}

		return "quantri/quanly/sanpham";
	}

	@RequestMapping(value = "sanpham/{maSP}", params = "suaThongtin", method = RequestMethod.GET)
	public String getSuaSanPham(ModelMap model, @PathVariable("maSP") Integer maSP) {

		SanPham sanpham = sanPhamService.getByMaSP(maSP);

		if (sanpham != null) {
			model.addAttribute("thongTinSP", sanpham);
			model.addAttribute("isOpenModalEdit", true);

		}

		return "quantri/quanly/sanpham";
	}

	@RequestMapping(value = "sanpham/{maSP}", params = "suaSP", method = RequestMethod.POST)
	public String postSuaSanPham(ModelMap model, @ModelAttribute("thongTinSP") SanPham sanpham,
			@RequestParam("anhMoi") MultipartFile anh, @PathVariable("maSP") Integer maSP, BindingResult errors) {

		if (sanpham.getTenSP().trim().isEmpty()) {
			errors.rejectValue("tenSP", "sanPhamMoi", "T??n s???n ph???m kh??ng ???????c ????? tr???ng");
		}

		if (sanpham.getGia() == 0) {
			errors.rejectValue("gia", "sanPhamMoi", "Gi?? kh??ng ???????c ????? tr???ng");
		}

		if (errors.hasErrors()) {
			model.addAttribute("isShowModalAddNew", true);
			return "quantri/quanly/sanpham";
		}

		SanPham sanphamcu = sanPhamService.getByMaSP(maSP);
		if (sanphamcu != null) {
			if (!sanphamcu.getTenSP().equals(sanpham.getTenSP()))
				sanphamcu.setTenSP(sanpham.getTenSP());
			if (sanphamcu.getDanhMuc() != sanpham.getDanhMuc()) {
				sanphamcu.setDanhMuc(sanpham.getDanhMuc());
			}
			if (sanphamcu.getPhai() != sanpham.getPhai()) {
				sanphamcu.setPhai(sanpham.getPhai());
			}
			if (!sanphamcu.getMoTa().equals(sanpham.getMoTa())) {
				sanphamcu.setMoTa(sanpham.getMoTa());
			}
			if (sanphamcu.getGia() != sanpham.getGia()) {
				sanphamcu.setGia(sanpham.getGia());
			}
			if (sanphamcu.getGiamGia() != sanpham.getGiamGia()) {
				sanphamcu.setGiamGia(sanpham.getGiamGia());
			}
			if (sanphamcu.getMauSanPham() != sanpham.getMauSanPham()) {
				sanphamcu.setMauSanPham(sanpham.getMauSanPham());
			}

			if (!anh.isEmpty()) {
				String hinh = "";
				hinh = utilService.luuFile(anh);
				if (!hinh.isEmpty()) {
					sanphamcu.setHinhAnh(hinh);
				}
			}

			if (sanPhamService.suaSP(sanphamcu)) {
				model.addAttribute("thongTinNV", new NhanVien());
				model.addAttribute("isSuccess", true);
				model.addAttribute("alertMessage", "S???a s???n ph???m th??nh c??ng");
				model.addAttribute("hinh", "");
				model.addAttribute("danhSachSanPham", sanPhamService.getDSSanPham());
			}
		}

		else {
			model.addAttribute("isSuccess", false);
			model.addAttribute("alertMessage", "S???a s???n ph???m th???t b???i");
		}

		return "quantri/quanly/sanpham";
	}
}
