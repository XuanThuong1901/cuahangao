package cnpm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.log.UserDataHelper.Mode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cnpm.dao.ChiTietSanPhamDAO;
import cnpm.entity.ChiTietDonHang;
import cnpm.entity.ChiTietDonHangPK;
import cnpm.entity.ChiTietSanPham;
import cnpm.entity.DonHang;
import cnpm.entity.GioHang;
import cnpm.entity.GioHangPK;
import cnpm.entity.HinhThucThanhToan;
import cnpm.entity.KhachHang;
import cnpm.entity.NhanVien;
import cnpm.entity.TaiKhoan;
import cnpm.entity.TrangThaiDonHang;
import cnpm.service.ChiTietDonHangService;
import cnpm.service.ChiTietSanPhamService;
import cnpm.service.DonHangService;
import cnpm.service.GioHangService;
import cnpm.service.HinhThucThanhToanServie;
import cnpm.service.KhachHangService;
import cnpm.service.TaiKhoanService;
import cnpm.service.TrangThaiDonHangService;

@Controller

public class KhachHangController {
	@Autowired
	TaiKhoanService taiKhoanService;
	
	@Autowired
	KhachHangService khachHangService;

	@Autowired
	GioHangService gioHangService;
	
	@Autowired
	HinhThucThanhToanServie hinhThucThanhToanServie;
	
	@Autowired
	TrangThaiDonHangService trangThaiDonHangService;
	
	@Autowired
	DonHangService donHangService;
	
	@Autowired
	ChiTietDonHangService chiTietDonHangService;
	@Autowired
	ChiTietSanPhamService chiTietSanPhamService;
	
	@ModelAttribute("danhSachGioHang")
	public List<GioHang> getDSGioHang(HttpSession ss) {
		TaiKhoan tk = (TaiKhoan) ss.getAttribute("user");
		List<GioHang> list = gioHangService.getDSGioHang(tk.getKhachHang().getMaKH());
		if(list == null) {
			return null;
		}
		double tongtien=0;
		for(int i=0; i<list.size(); i++) {
			tongtien += (double)(list.get(i).getSoLuong()*list.get(i).getChiTietSP().getSanPham().getGia());
		}
		double tongdon = tongtien + (double)21000;
		ss.setAttribute("tongtien", tongtien);
		ss.setAttribute("tongdon", tongdon);
		return list;
	}
	
	@ModelAttribute("danhSachHinhThucThanhToan")
	public List<HinhThucThanhToan> danhSachHTTT(){
		return hinhThucThanhToanServie.getDSHinhThucThanhToan();
	}

	@ModelAttribute("thongTinKH")
	public KhachHang thongtinKH(ModelMap model) {
		return new KhachHang();
	}

	@RequestMapping("taikhoan")
	public String getViewTaiKhoan(ModelMap model, HttpSession ss) {

		TaiKhoan tk = (TaiKhoan) ss.getAttribute("user");
		if(tk == null) {
			return "taikhoan/dangnhap";
		}
		return "shop/taikhoan";
	}

	@RequestMapping("thanhtoan")
	public String getViewThanhToan(ModelMap model) {

		return "shop/thanhtoan";
	}

	@RequestMapping("giohang")
	public String getViewGioHang(ModelMap model, HttpSession ss) {

		TaiKhoan tk = (TaiKhoan) ss.getAttribute("user");
		if(tk == null) {
			return "taikhoan/dangnhap";
		}
		return "shop/giohang";
	}
	
// 
	@RequestMapping(value = "chitietsanpham/{maSP}", params = "themvaogio", method = RequestMethod.POST)
	public String themGioHang(ModelMap model, HttpServletRequest request, @PathVariable("maSP") Integer maSP,
			@RequestParam("size") Integer size, @RequestParam("soluong") Integer soLuong,
			HttpSession ss) {
		/// check chi cho khach hang dc them

		if(soLuong == 0) {
			model.addAttribute("soluong", "S??? l?????ng ph???i l???n h??n 0");
			return "shop/chitietsanpham";
		}
		
		ChiTietSanPham ctsp = chiTietSanPhamService.getByMaSPandMaSize(maSP, size);
		TaiKhoan tk = (TaiKhoan) ss.getAttribute("user");
		
		System.out.println(ctsp.getMaChiTietSP());
		
		GioHang gioHang = gioHangService.get1GioHang(tk.getKhachHang().getMaKH(), ctsp.getMaChiTietSP());
		if(gioHang == null) {
			gioHang = new GioHang();
			GioHangPK gioHangpk = new GioHangPK();
			gioHangpk.setMaCTSP(ctsp.getMaChiTietSP());
			gioHangpk.setMaKH(tk.getKhachHang().getMaKH());
			
			gioHang.setGioHangPK(gioHangpk);
			gioHang.setChiTietSP(ctsp);
			gioHang.setKhangHang(tk.getKhachHang());
			gioHang.setSoLuong(soLuong);
			
			if(gioHangService.themGH(gioHang)) {
				model.addAttribute("themgiohang", "Th??m s???n ph???m th??nh c??ng");
			}
			else {
				model.addAttribute("themgiohang", "Th??m s???n ph???m th???t b???i");
			}
		}
		else {
			gioHang.setSoLuong(gioHang.getSoLuong()+soLuong);
			if(gioHangService.suaGH(gioHang)) {
				model.addAttribute("themgiohang", "Th??m s???n ph???m th??nh c??ng");
			}
			else {
				model.addAttribute("themgiohang", "Th??m s???n ph???m th???t b???i");
			}
		}
		
		return "shop/chitietsanpham";

	}

	@RequestMapping(value = "taikhoan", params = "suathongtin", method = RequestMethod.POST)
	public String postSuaKhachHang(ModelMap model, HttpSession ss, @RequestParam("ho") String ho,
			@RequestParam("ten") String ten, @RequestParam("sdt") String sdt, @RequestParam("diaChi") String diaChi) {

		try {
			TaiKhoan tk = (TaiKhoan) ss.getAttribute("user");

			KhachHang kh = tk.getKhachHang();
			kh.setHo(ho);
			kh.setTen(ten);
			kh.setSdt(sdt);
			kh.setDiaChi(diaChi);

			khachHangService.suaKH(kh);
			model.addAttribute("suatk", "Thay ?????i t??i kho???n th??nh c??ng");
		} catch (Exception e) {
			model.addAttribute("suatk", "Thay ?????i t??i kho???n th???t b???i");
		}

		return "shop/taikhoan";
	}

	@RequestMapping(value = "taikhoan", params = "doimatkhau", method = RequestMethod.POST)
	public String postDoiMatKhau(ModelMap model, HttpSession ss, @RequestParam("mkcu") String mkcu,
			@RequestParam("mkmoi") String mkmoi, @RequestParam("xacnhanmk") String xacnhanmk) {

		if (mkcu == "") {
			model.addAttribute("mkcu", "Ch??a nh???p m???t kh???u hi???n t???i");
			return "shop/taikhoan";
		}

		if (mkmoi == "") {
			model.addAttribute("mkmoi", "Ch??a nh???p m???t kh???u m???i");
			return "shop/taikhoan";
		}

		if (xacnhanmk == "") {
			model.addAttribute("xacnhanmk", "Ch??a nh???p m???t kh???u x??c nh???n");
			return "shop/taikhoan";
		}

		if (!mkmoi.equals(xacnhanmk)) {
			model.addAttribute("xacnhanmk", "M???t kh???u v?? m???t kh???u x??c nh???n kh??c nhau");
			return "shop/taikhoan";
		}

		TaiKhoan tk = (TaiKhoan) ss.getAttribute("user");

		if(taiKhoanService.kiemTraDangNhap(tk.getEmail(), mkcu)) {
			taiKhoanService.thayDoiMK(tk, mkmoi);
			model.addAttribute("suamk", "Thay ?????i th??nh c??ng");
		}
		else {
			model.addAttribute("suamk", "Thay ?????i th???t b???i");
		}
		return "shop/taikhoan";
	}
	
	@RequestMapping(value = "giohang", params = "update", method = RequestMethod.POST)
	public String postUpdateGH(ModelMap model, HttpSession ss, 
			@RequestParam("soluong") Integer soluong) {
		
		
		
		TaiKhoan tk = (TaiKhoan) ss.getAttribute("user");
		
//		GioHang giohang = gioHangService.get1GioHang(tk.getKhachHang().getMaKH(), mactsp);
		
//		gioHangService.xoaGH(giohang);
		
		return "shop/giohang";
	}
	
	@RequestMapping(value = "giohang", params = "xoagiohang", method = RequestMethod.POST)
	public String postXoaGioHang(ModelMap model, HttpSession ss, @RequestParam("xoagiohang") Integer mactsp) {

		TaiKhoan tk = (TaiKhoan) ss.getAttribute("user");
		
		GioHang giohang = gioHangService.get1GioHang(tk.getKhachHang().getMaKH(), mactsp);
		
		gioHangService.xoaGH(giohang);
		model.addAttribute("danhSachGioHang", gioHangService.getDSGioHang(tk.getKhachHang().getMaKH()));
		
		return "shop/giohang";
	}
	
	@RequestMapping(value = "giohang", params = "thanhtoan", method = RequestMethod.POST)
	public String thanhToan(ModelMap model, HttpServletRequest request,
			@RequestParam("httt") Integer httt, HttpSession ss,
			 @RequestParam(name="ghichu", required = false) String ghichu) {
		/// check chi cho khach hang dc them

		TaiKhoan tk = (TaiKhoan) ss.getAttribute("user");
		
		List<GioHang> listgh = gioHangService.getDSGioHang(tk.getKhachHang().getMaKH());
		
		if(listgh == null) {
			return "shop/giohang";
		}
		
		DonHang donhang = new DonHang();
		TrangThaiDonHang trangthaidh = trangThaiDonHangService.getByMaTrangThaiDonHangService(1);
		
		donhang.setKhachHang(tk.getKhachHang());
		donhang.setDiaChi(tk.getKhachHang().getDiaChi());
		donhang.setHinhThucTT(hinhThucThanhToanServie.getByMaHinhThucThanhToan(httt));
		java.util.Date date = new java.util.Date();
		donhang.setThoiGian(date);
		donhang.setSdtKH(tk.getKhachHang().getSdt());
		donhang.setTrangThaiDH(trangthaidh);
		
		String tongtien = ss.getAttribute("tongdon").toString();
		double tt = Double.parseDouble(tongtien);
		
		donhang.setTongTien(tt);
		System.out.println(tt);
		
		if(donHangService.themDH(donhang)) {
			System.out.println(1);
			int madh = donHangService.maPNCuoiCung();
			donhang = donHangService.getByMaDH(madh);
			ChiTietDonHang ctdh = new ChiTietDonHang();
			System.out.println(2);
			ChiTietDonHangPK ctdhpk = new ChiTietDonHangPK();
			for(int i=0; i<listgh.size();i++) {
				System.out.println(3);
				ctdh.setDonHang(donhang);
				ctdh.setChiTietSP(listgh.get(i).getChiTietSP());
				ctdh.setSoLuong(listgh.get(i).getSoLuong());
				ctdh.setGia(listgh.get(i).getChiTietSP().getSanPham().getGia());
				ctdhpk.setMaCTSP(listgh.get(i).getChiTietSP().getMaChiTietSP());
				ctdhpk.setMaDH(madh);
				ctdh.setChiTietDonHangPK(ctdhpk);
				
				if(chiTietDonHangService.themCTDH(ctdh)) {
					System.out.println(4);
					gioHangService.xoaGH(listgh.get(i));
					System.out.println(4);
				}
				else {
					System.out.println(5);
					model.addAttribute("muahang", "Mua h??ng th???t b???i");
					return "shop/giohang";
				}
				
			}
			
			model.addAttribute("muahang", "Mua h??ng th??nh c??ng th??nh c??ng");
			model.addAttribute("danhSachGioHang", gioHangService.getDSGioHang(tk.getKhachHang().getMaKH()));
		}
		else {
			model.addAttribute("muahang", "Mua h??ng th???t b???i");
		}
		
		
		return "shop/giohang";

	}
}
